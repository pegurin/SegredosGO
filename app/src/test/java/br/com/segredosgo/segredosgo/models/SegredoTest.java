package br.com.segredosgo.segredosgo.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by lucasaciole on 10/12/2017.
 */

public class SegredoTest {

    Segredo testSecret;

    @Before
    public void intializeSecret() {
        testSecret = new Segredo();
    }

    @Test
    public void setsCorrectTitle() throws Exception {
        testSecret.setTitulo("Segredo Teste");
        assertEquals("Segredo Teste", testSecret.getTitulo());
    }

    @Test
    public void setsCorrectDescription() throws Exception {
        testSecret.setDescricao("Segredo Teste Descrição");
        assertEquals("Segredo Teste Descrição", testSecret.getDescricao());
    }
}
