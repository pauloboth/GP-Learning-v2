package com.gplearning.gplearning.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.gplearning.gplearning.Adapters.Comentario2Adapter;
import com.gplearning.gplearning.Adapters.ComentarioAdapter;
import com.gplearning.gplearning.DAO.App;
import com.gplearning.gplearning.Models.Comentario;
import com.gplearning.gplearning.Models.ComentarioDao;
import com.gplearning.gplearning.Models.DaoSession;
import com.gplearning.gplearning.Models.Value;
import com.gplearning.gplearning.Models.ValueDao;
import com.gplearning.gplearning.R;
import com.gplearning.gplearning.Utils.MetodosPublicos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComentarioActivity extends AppCompatActivity {

    private List<Comentario> lsComentario = new ArrayList<>();
    private ComentarioDao dao;
    private RecyclerView recyclerView;
    private ComentarioAdapter comentarioAdapter; //ArrayAdapter<Comentario> comentario2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        setTitle(R.string.comments);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        //   ComentarioDao cDao
        dao = daoSession.getComentarioDao();
        recyclerView = (RecyclerView) findViewById(R.id.comentarioListView);

        lsComentario = dao.queryBuilder().orderAsc(ComentarioDao.Properties.Criacao).list();
        comentarioAdapter = new ComentarioAdapter(lsComentario, this); // new ArrayAdapter<Comentario>(this, android.R.layout.simple_list_item_1, lsComentario);
        recyclerView.setAdapter(comentarioAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new MetodosPublicos.RecyclerItemClickListener(this, recyclerView, new MetodosPublicos.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("Event", "Clicou");
            }
            @Override
            public void onLongItemClick(View view, int position) {
                Log.i("Event", "Long Click");
                PopupDeletaComentario(position);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void SalvaComentario(View view) {
        EditText coment = (EditText) findViewById(R.id.comentarioNovo);
        try {
            if (!coment.getText().toString().isEmpty()) {
                Comentario COM = new Comentario(null, null, coment.getText().toString(), new Date());

                Value val = new Value(null, coment.getText().toString());
                long id = dao.insert(COM);
                Log.i("Event", "id:" + COM.get_id());
                if (id > 0) {
                    coment.setText("");
                    lsComentario.add(COM);
                    comentarioAdapter.notifyItemInserted(lsComentario.size() - 1);
                    recyclerView.smoothScrollToPosition(lsComentario.size() - 1);
                }
            }
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    public void PopupDeletaComentario(final int position) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.comment_delete);
        alert.setCancelable(true);
        alert.setNeutralButton(R.string.cancel, null);
        alert.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Log.i("Event", "Vai deletar o comet id:" + comentarioAdapter.getItemId(position));
                    dao.deleteByKey(comentarioAdapter.getItemId(position));
                    lsComentario.remove(position);
                    comentarioAdapter.notifyItemRemoved(position);
                } catch (Exception e) {
                    Log.e("ERROR", e.toString());
                }
            }
        });
        alert.show();

    }


}