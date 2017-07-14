package service;

import java.util.Collection;

import model.Flower;

public interface FlowerService {
	public Collection<Flower> getAll();
	public Flower getFlowerByName(String name);
	public void deleteAll();
	public void saveFlower(Flower flower);
}
