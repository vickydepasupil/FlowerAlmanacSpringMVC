package controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dropbox.core.*;

import model.Flower;
import service.FlowerService;
import service.MockFlowerService;

@Controller
@RequestMapping("/home")
public class FlowerController {
	private FlowerService flowerService = new MockFlowerService(); 
	
	final String APP_KEY = "77dykoko5st852n";
	final String APP_SECRET = "31kvdt1y8id6f3s";
	
	DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
	DbxRequestConfig config = new DbxRequestConfig("FlowerAlmanac/1.0", Locale.getDefault().toString());
	DbxClient client;
	
	String sessionKey = "dropbox-auth-csrf-token";
	
	@RequestMapping(value="authorize", method=RequestMethod.GET)
	public String getAuthPage() {
		return "Connect";
	}
	
	@RequestMapping(value="/process", method=RequestMethod.POST)
	public ModelAndView authorize(HttpServletRequest request, HttpSession session) throws IOException, DbxException {
		
		session = request.getSession(true);
		DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
		
		String redirectURL = "http://localhost:8080/FlowerAlamanac/home/welcome";
		DbxWebAuth webAuth = new DbxWebAuth(config, appInfo, redirectURL, csrfTokenStore);
		
		String authorize = webAuth.start();
		
		return new ModelAndView("redirect:"+authorize);
	}
	
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String getWelcome(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws DbxException, IOException {

		DbxAuthFinish authFinish = null;
		 
		session = request.getSession(true);
		DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
			
		String redirectURL = "http://localhost:8080/FlowerAlamanac/home/welcome";
		DbxWebAuth webAuth = new DbxWebAuth(config, appInfo, redirectURL, csrfTokenStore);
		 
		try {
		     authFinish = webAuth.finish(request.getParameterMap());
		}
		catch (DbxWebAuth.BadRequestException ex) {
		}
		catch (DbxWebAuth.BadStateException ex) {
		}
		catch (DbxWebAuth.CsrfException ex) {
		}
		catch (DbxWebAuth.NotApprovedException ex) {
		}
		catch (DbxWebAuth.ProviderException ex) {
		}
		catch (DbxException ex) {
		}
		String accessToken = authFinish.accessToken;

		client = new DbxClient(config, accessToken);
		
		DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        
        flowerService.deleteAll();
        
        for (DbxEntry child : listing.children) {
        	if (!child.name.equals("database.txt")) {
        	Flower flower = new Flower();
        	
        	String finalName = child.name.replace(".jpg", "");        	
        	
        	flower.setName(finalName);
        	flower.setPath(child.path);
        	flower.setRev(child.asFile().rev);
            flowerService.saveFlower(flower);
        	} else {
        		FileOutputStream outputStream = new FileOutputStream("database.txt"); // downloads to desktop
                try {
                    DbxEntry.File downloadedFile = client.getFile("/database.txt", null, outputStream);
                } finally {
                    outputStream.close();
                }
        	}
        }
        
        String path = "C:/Users/victo/Desktop/database.txt";
        
        FileReader fileReader = new FileReader(path);
        BufferedReader dataFile = new BufferedReader(fileReader);
        
        do{
        	for (int i = 0; i < 3; i++) {
            	String name = dataFile.readLine();
            	String ease = dataFile.readLine();
            	String inst = dataFile.readLine();
            	
            	Flower flower = flowerService.getFlowerByName(name);
            	flower.setEase(ease);
            	flower.setInst(inst);
            	flowerService.saveFlower(flower);
            	
            	System.out.println("name: "+name);
            	System.out.println("ease: "+ease);
            	System.out.println("inst: "+inst);
            }
        	
        } while(dataFile.readLine()!=null);
        	       
		return "Welcome"; //returns Welcome.jsp
	}
	
	@RequestMapping("/view")
	public ModelAndView getAll() {
		Collection<Flower> flowerList = flowerService.getAll();
			ModelAndView model = new ModelAndView("ViewPhotos"); // this is the JSP page
			model.addObject("flowers", flowerList);
			
			return model;
	}
	
	@RequestMapping(value="/photo/{path}/{rev}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] showPhoto(@PathVariable String path, @PathVariable String rev) {
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		try {
			DbxEntry.File file = client.getThumbnail(DbxThumbnailSize.w640h480, DbxThumbnailFormat.JPEG, "/"+path, rev, fileOut);
		} catch(IOException ie) { ie.printStackTrace();
		} catch(DbxException de) { de.printStackTrace();
		} 
		byte[] photo = fileOut.toByteArray();
		return photo;
	}
}
