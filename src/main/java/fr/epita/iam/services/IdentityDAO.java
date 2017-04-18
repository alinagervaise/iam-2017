package fr.epita.iam.services;

import java.util.List;

import fr.epita.iam.datamodel.Identity;

public interface IdentityDAO {

	public void create(Identity entity);
	public void update(Identity entity);
	public void delete(Identity entity);
	public List<Identity> readAll();
}
