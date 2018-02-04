package com.onek.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onek.model.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao, Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addEmployee(Employee emp) {
		System.out.println("DAO called - insert of : " + emp.getFirstName());

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(emp);
		session.getTransaction().commit();
		session.close();

		System.out.println("Add done");
	}

	@Override
	public Employee findEmployeeByFirstName(String firstName) {
		Employee result = new Employee();

		Session session = sessionFactory.openSession();
        session.beginTransaction();
        result = (Employee) session.createQuery("from Employee where firstName = :firstName").setParameter("firstName", firstName).list().get(0);
        session.getTransaction().commit();
        session.close();
        
		System.out.println("Find done - firstName: " + result.getFirstName());
		return result;
	}
}