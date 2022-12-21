package com.example.bank.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseService {

	@PersistenceContext
	protected EntityManager entityManager;

}
