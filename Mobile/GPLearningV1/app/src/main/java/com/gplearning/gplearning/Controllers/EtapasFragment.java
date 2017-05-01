package com.gplearning.gplearning.Controllers;

//import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gplearning.gplearning.DAO.App;
import com.gplearning.gplearning.Enums.EtapaProjeto;
import com.gplearning.gplearning.Models.Atividade;
import com.gplearning.gplearning.Models.AtividadeDao;
import com.gplearning.gplearning.Models.DaoSession;
import com.gplearning.gplearning.Models.Pessoa;
import com.gplearning.gplearning.Models.PessoaDao;
import com.gplearning.gplearning.R;

import java.lang.reflect.Field;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EtapasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EtapasFragment#} factory method to
 * create an instance of this fragment.
 */
public class EtapasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "projetoId";

    //  private List<Atividade> lsAtividades;
    private Long projetoId;
    private AtividadeDao atividadeDao;
    private OnFragmentInteractionListener mListener;

    public EtapasFragment() {
    }

    //    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @return A new instance of fragment EtapasFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static EtapasFragment newInstance(Long projetoId) {
        EtapasFragment fragment = new EtapasFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, projetoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            projetoId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_etapas, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.project_detail);
        DaoSession session = ((App) getActivity().getApplication()).getDaoSession();
        PessoaDao pessoaDao = session.getPessoaDao();
        List<Pessoa> lsPessoas = pessoaDao.loadAll();

        ((TextView) getActivity().findViewById(R.id.etapaProjetoNomeTxt)).setText("Nome do projeto");
        ((TextView) getActivity().findViewById(R.id.etapaProjetoEmpresaTxt)).setText("Nome da Empresa");
        ((TextView) getActivity().findViewById(R.id.etapaProjetoGerenteTxt)).setText("Nome do Gerente");


        atividadeDao = session.getAtividadeDao();
        List<Atividade> lsAtividades = atividadeDao.queryBuilder().where(AtividadeDao.Properties.Pro_id.eq(projetoId)).list();
        PassaValoresEtapas(lsAtividades);

        ((ImageButton) getActivity().findViewById(R.id.etapaProjetoDescricaoTAbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EtapaActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /// Icones
    /// http://www.flaticon.com/packs/business-and-finance-11
    /// http://www.flaticon.com/packs/reports-and-analytics/2
    private void PassaValoresEtapas(List<Atividade> lsAtividades) {


        // descricao
        //justificativa

        // premissas

        //restrições
        //marcos

        //requisitos Termo de abertura
        //partes interessadas
        //

        //Planejamento de Escopo --http://www.flaticon.com/free-icon/brainstorm_201557#term=strategy&page=1&position=2
        // requisitos --  http://www.flaticon.com/free-icon/clipboard_235252
        // escopo  --  http://www.flaticon.com/free-icon/notebook_330705


        // Atividade atv = atividadeDao.queryBuilder().where(AtividadeDao.Properties.Pro_id.eq(projetoId), AtividadeDao.Properties.Etapa.eq(EtapaProjeto.TermoAberturaDescricao)).unique();
        if (lsAtividades != null && lsAtividades.size() > 0) {

            for (Atividade atv : lsAtividades) {
                if (atv.getEtapa() == EtapaProjeto.TermoAberturaDescricao)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoDescricaoTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.TermoAberturaJustificativa)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoJustificativaTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.TermoAberturaPremissas)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoPremissasTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.TermoAberturaRestricoes)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoRestricoesTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.TermoAberturaMarcos)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoMarcosTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.TermoAberturaRequisitos)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoRequisitosTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.Stakeholders)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoStakeholdersTARatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.PlanejamentoEscopo)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoEscopoRatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.Requisitos)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoRequisitosRatingBar)).setRating(atv.getPontuacaoMedia());
                else if (atv.getEtapa() == EtapaProjeto.Escopo)
                    ((RatingBar) getActivity().findViewById(R.id.etapaProjetoEscopoRatingBar)).setRating(atv.getPontuacaoMedia());
            }
            //        atv = atividadeDao.queryBuilder().where(AtividadeDao.Properties.Pro_id.eq(projetoId), AtividadeDao.Properties.Etapa.eq(EtapaProjeto.TermoAberturaJustificativa)).unique();
            //        if (atv != null)
            //            ((RatingBar) getActivity().findViewById(R.id.etapaprojetoJu)).setRating(atv.getPontuacaoMedia());
        }
    }

}


