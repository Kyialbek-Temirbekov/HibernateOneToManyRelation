package org.example;

import org.example.model.Director;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Director director1 = session.get(Director.class, 1);
            System.out.println(director1);
            List<Movie> movies = director1.getMovies();
            System.out.println(movies);

            Movie movie2 = session.get(Movie.class, 11);
            System.out.println(movie2);
            Director director2 = movie2.getDirector();
            System.out.println(director2);

            Director director3 = session.get(Director.class, 6);
            Movie movie3 = new Movie("Titanic", 1982, director3);
            director3.getMovies().add(movie3);
            session.persist(movie3);

            Director director4 = new Director("Holan", 55);
            Movie movie4 = new Movie("Interstellar", 2022, director4);
            director4.setMovies(new ArrayList<>(Collections.singletonList(movie4)));
            session.persist(director4);
            session.persist(movie4);

            Director director5 = session.get(Director.class, 7);
            Movie movie5 = session.get(Movie.class, 1);
            movie5.getDirector().getMovies().remove(movie5);
            movie5.setDirector(director5);
            director5.getMovies().add(movie5);

            Movie movie6 = session.get(Movie.class, 1);
            Director director6 = movie6.getDirector();
            director6.getMovies().remove(movie6);
            session.remove(movie6);

            session.getTransaction().commit();
        }
    }
}
