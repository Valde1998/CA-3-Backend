package notes;

import notes.config.ApplicationConfig;
import notes.config.HibernateConfig;
import notes.config.Routs;
import notes.ressources.Category;
import notes.ressources.Note;
import notes.ressources.Role;
import notes.ressources.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class main {
    public static void main(String[] args) {
        startServer(7070);
    }

    public static void startServer(int port) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance(emf);
        setup(emf);
        applicationConfig
                .initiateServer()
                .startServer(port)
                .setExceptionHandling()
                .checkSecurityRoles()
                .configureCors()
                .setRoute(Routs.unsecuredRoutes(emf))
                .setRoute(Routs.getSecurityRoutes(emf))
                .setRoute(Routs.securedRoutes(emf))
            ;
        //Comit
    }
    

    private static void setup(EntityManagerFactory emf){
        try (EntityManager em = emf.createEntityManager()) {
            // if(em.find(Note.class, 1) != null) return;
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Note h").executeUpdate();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createQuery("DELETE FROM Role r").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE note_id_seq RESTART WITH 1").executeUpdate();  
            
            Note n1 = new Note("n1", "THIS IS NOTE 1", Category.NOTE);
            Note n2 = new Note("n2", "THIS IS NOTE 2", Category.REMINDER);
            Note n3 = new Note("a3", "THIS IS NOTE 4", Category.NOTE);
            Note n4 = new Note("b3", "THIS IS", Category.REMINDER);
            Note n5 = new Note("3", "THIS IS NOTE 3", Category.NOTE);
            Note n6 = new Note("3", "THIS IS NOTE 3", Category.REMINDER);
            

            User admin = new User("admin", "1234"); //TODO bedere sequrety, admin credentails shuld not be this visible
            User u1 = new User("u1", "1234");
            User u2 = new User("u2", "1234");
            User u3 = new User("u3", "1234");
            User u4 = new User("u4", "1234");
        

            admin.addNote(n1);
            admin.addNote(n2);
            admin.addNote(n3);
            admin.addNote(n4);
            admin.addNote(n5);
            admin.addNote(n6);
            u1.addNote(n1);
            u2.addNote(n2);
            System.out.println(admin.getNotes());

            Role adminRole = new Role("admin");
            Role userRole = new Role("user");
            admin.addRole(adminRole);
            u1.addRole(userRole);
            u2.addRole(userRole);
            u3.addRole(userRole);
            u4.addRole(userRole);
            
            
            em.persist(n1);
            em.persist(n2);
            em.persist(n3);
            em.persist(n4);
            em.persist(n5);
            em.persist(n6);
            em.persist(admin);
            em.persist(adminRole);
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.persist(u4);

            em.getTransaction().commit();
        }
    }

}
