package net.osgg;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.osgg.entity.Estudiante;
import net.osgg.util.HibernateUtil;

public class App {
    public static void main(String[] args) {

        Estudiante est1 = new Estudiante("José", "Cruz", "jos@hihi.com", "software");
        Estudiante est2 = new Estudiante("María", "Paz", "mar@hihi.com", "software");
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the object
            session.save(est1);
            session.save(est2);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List < Estudiante > estudiantes = session.createQuery("from Estudiante", Estudiante.class).list();
            estudiantes.forEach(s -> System.out.println("Nombre: " + s.getNombre()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}