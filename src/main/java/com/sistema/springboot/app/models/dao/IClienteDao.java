package com.sistema.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sistema.springboot.app.models.entity.Cliente;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

	/*
	public List<Cliente> findAll();
	
	public void save (Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);*/
}
