package service;

import java.util.Collection;
import java.util.HashMap;

import model.Flower;

public class MockFlowerService implements FlowerService{
	private static HashMap<String, Flower> flowerList = new HashMap<String, Flower>();

	@Override
	public Collection<Flower> getAll() {
		return flowerList.values();
	}
	
	@Override
	public Flower getFlowerByName(String name) {
		Flower flower = flowerList.get(name);
		return new Flower(flower.getName(), flower.getPath(),
				flower.getRev(), flower.getEase(), flower.getInst());
	}
	
	public void deleteAll() {
		flowerList.clear();
	}

	@Override
	public void saveFlower(Flower flower) {
		String temp = flower.getName();
		
		flower.setName(temp);
		flowerList.put(temp, flower);
	}
}
