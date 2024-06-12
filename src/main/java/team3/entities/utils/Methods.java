//package team3.entities.utils;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityTransaction;
//import team3.dao.UserDao;
//import team3.entities.user.UserClass;
//
//public class Methods {
//
//    public static void createUserFromInput(String name, String surname, EntityManager em) {
//        EntityTransaction transaction = em.getTransaction();
//        UserDao ud = new UserDao(em);
//        try {
//            UserClass newUser = new UserClass(name, surname);
//            ud.save(newUser);
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//}
