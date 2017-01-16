package com.allstate.services;

import com.allstate.entities.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class MovieServiceTest {

    @Autowired
    private MovieService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCreateMovie() throws Exception {

        Movie before = new Movie();
        before.setTitle("The Matrix");
        Movie after = this.service.create(before);
        assertEquals(2, after.getId());
        assertEquals(0, after.getVersion());
        assertEquals("The Matrix", after.getTitle());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotCreateMovieNoTitle() throws Exception {
        Movie before = new Movie();
        Movie after = this.service.create(before);
        assertEquals(2, after.getId());
    }

    @Test
    public void shouldFindMovieByID(){
        Movie m = this.service.findById(1);
        assertNotNull(m);
    }

    @Test
    public void shouldFindAllMovie(){
        ArrayList<Movie>  movie = (ArrayList<Movie>) this.service.findAll();
        assertEquals(1, movie.size());
    }

    @Test
    public void shouldReturnMovieTitle() throws Exception {
    Movie m = this.service.findByTitle("Ring");
        assertNotNull(m);
    }

    @Test
    public void shouldReturnBadMovieTitle() throws Exception {
        Movie m = this.service.findByTitle("Avengers");
        assertNotNull(m);
    }

    @Test
    public void shouldDeleteMovieById() throws Exception{
        this.service.delete(1);
        assertNull(this.service.findById(1));
        assertNotNull(this.service.findById(1));
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotDeleteMovieById() throws Exception{
        this.service.delete(5);
    }

    @Test
    public void shouldUpdateMovie() throws Exception {
        Movie before = new Movie();
        before.setTitle("The Avengers II");
        Movie after = this.service.update(1, before);
        assertEquals(1, after.getVersion());
        assertEquals("The Avengers II", after.getTitle());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotUpdateMovieNoTitle() throws Exception {
        Movie before = new Movie();
        before.setTitle(null);
        Movie after = this.service.update(1, before);
    }
}