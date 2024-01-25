package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.modelo.Estudiante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class EstudianteRepositoryImpl implements IEstudianteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Estudiante estudiante) {
		this.entityManager.persist(estudiante);
	}

	@Override
	public void actualizar(Estudiante estudiante) {
		this.entityManager.merge(estudiante);
	}

	@Override
	public void actualizarParcial(String apellido, String nombre, Integer id) {
		// JPQL: objetos y atributos
		// SQL: UPDATE estudiante e SET e.estu_nombre=:valor, e.estu_apellido:valor2 WHERE e.estu_id=:valor3
		Query query = this.entityManager.createQuery("UPDATE Estudiante e SET e.nombre = :valor1, e.apellido=:valor2 WHERE e.id=:valor3");
		query.setParameter("valor1", nombre);
		query.setParameter("valor2", apellido);
		query.setParameter("valor3", id);
		query.executeUpdate(); //este puede retornar un entero con el número de actualizaciones hechas.
	}

	@Override
	public Estudiante seleccionar(Integer id) { // Buscar por ID
		return this.entityManager.find(Estudiante.class, id);
	}

	@Override
	public void eliminar(Integer id) {
		this.entityManager.remove(this.seleccionar(id));
	}

}
