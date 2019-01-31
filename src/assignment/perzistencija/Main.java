package assignment.perzistencija;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

    public static void main(String[] args) {

        MyJFrame frame = new MyJFrame();
        frame.setVisible(true);

    }

    public static void createNewEmployee(String name, int age, String address, double salary) {

        Session session = HiberanteUtil.createSessionFactory().openSession();
        Transaction tx = null;

        Employee employee = new Employee(name, age, address, salary);
        try {
            tx = session.beginTransaction();

            session.persist(employee);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HiberanteUtil.close();
        }
    }

    public static List<Employee> selectAllEmployees() {

        Session session = HiberanteUtil.createSessionFactory().openSession();
        Transaction tx = null;

        String hql = "from Employee";
        Query query = session.createQuery(hql);

        List<Employee> employees_list = null;

        try {
            tx = session.beginTransaction();

            employees_list = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HiberanteUtil.close();
        }

        return employees_list;
    }

    public static List<Employee> searchEmployeesByName(String name) {

        Session session = HiberanteUtil.createSessionFactory().openSession();
        Transaction tx = null;

        String hql = "from Employee employee where employee.name like :nameInput";
        Query query = session.createQuery(hql);
        query.setParameter("nameInput", "%"+name+"%");

        List<Employee> employees_list = null;

        try {
            tx = session.beginTransaction();

            employees_list = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HiberanteUtil.close();
        }

        return employees_list;
    }

    public static void updateEmployee(String id, String property, String value) {
        Session session = HiberanteUtil.createSessionFactory().openSession();
        Transaction tx = null;
        String hql = "update Employee employee set employee." + property + " = "+value+" where employee.id = "+id;
        Query query = session.createQuery(hql);
        try {
            tx = session.beginTransaction();

            query.executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HiberanteUtil.close();
        }
    }

    public static void deleteEmployee(String id) {

        Session session = HiberanteUtil.createSessionFactory().openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            Employee employee = (Employee)session.load(Employee.class, Integer.valueOf(id));
            
            session.delete(employee);
            
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e);
        } finally {
            HiberanteUtil.close();
        }
    }
}
