package com.gplearning.gplearning.DAO;


import android.util.Log;

import com.gplearning.gplearning.Models.DaoSession;
import com.gplearning.gplearning.Models.Pessoa;
import com.gplearning.gplearning.Models.PessoaDao;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DefaultDAO {

    String url = UrlDefault;

    public Pessoa Login(DaoSession session, String email, String senha) {
        url += "/login/login/" + email + "/" + senha;
        RestTemplate restTemplate = getResTemplateDefault();

        Log.i("login", "Url: " + url);
        Pessoa user = restTemplate.getForObject(url, Pessoa.class); //("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);

        //  Pessoa user = null;// = response.
        if (user != null && user.getId() > 0) {
            PessoaDao dao = session.getPessoaDao();
            dao.insert(user);
            return user;
        }
        return null;
    }


    public List<Pessoa> Select() {

        List<Pessoa> lsUsuario = new ArrayList<>();

        // String url = UrlDefau;
        return lsUsuario;
    }


}
