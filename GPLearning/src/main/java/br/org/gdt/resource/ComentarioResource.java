/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.gdt.resource;

import br.org.gdt.bll.AtividadeBLL;
import br.org.gdt.model.Comentario;
import br.org.gdt.bll.ComentarioBLL;
import br.org.gdt.model.Atividade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/comentario")
public class ComentarioResource {

    @Autowired
    private ComentarioBLL comentarioBLL;

    @Autowired
    private AtividadeBLL atividadeBLL;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/index/{atv_id}")
    public List<Comentario> getAll(@PathParam("atv_id") int atv_id) {
        System.out.println("chegou no metodo id:" + atv_id);
        List<Comentario> lsComentario = new ArrayList<Comentario>();
        if (atv_id > 0) {
            Atividade atv = atividadeBLL.findById(atv_id);
            if (atv != null && atv.getId() > 0) {
                lsComentario = comentarioBLL.findbyAtividade(atv);
            }
        }
        return lsComentario;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/comentario/{com_id}")
    public Comentario getbyId(@PathParam("com_id") int com_id) {
        System.out.println("chegou no metodo id:" + com_id);
        //  List<Comentario> lsComentario = new ArrayList<Comentario>();
        return comentarioBLL.findById(com_id);
    }
    //  return lsComentario;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/salvar")
    public int Salvar(Comentario comentario) {
        if (comentario != null && comentario.getDescricao() != null) {
            if (comentario.getId() > 0) {
                comentarioBLL.update(comentario);
            } else {
                comentarioBLL.insert(comentario);
            }
            return comentario.getId();
        }
        return 0;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/excluir")
    public Boolean Excluir(@FormParam("com_id") int com_id) {
        if (com_id > 0) {
            Comentario comentario = comentarioBLL.findById(com_id);
            comentarioBLL.delete(comentario);
            return true;
        }
        return false;
    }

}
