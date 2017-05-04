package com.gplearning.gplearning.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


@Entity(nameInDb = "marco")
public class Marco {
    @Id
    private int id;

    private String objetivo;

    private Date previsao;

    private int IdTermoAbertura;
    @ToOne(joinProperty = "IdTermoAbertura")
    private TermoAbertura termoAbertura;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1715484190)
    private transient MarcoDao myDao;

    @Generated(hash = 1154405152)
    private transient Integer termoAbertura__resolvedKey;

    public Marco() {
    }

    @Keep
    public Marco(int id, String objetivo, Date previsao, int IdTermoAbertura) {
        this.id = id;
        this.objetivo = objetivo;
        this.previsao = previsao;
        this.IdTermoAbertura = IdTermoAbertura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getPrevisao() {
        return previsao;
    }

    public void setPrevisao(Date previsao) {
        this.previsao = previsao;
    }

    public int getIdTermoAbertura() {
        return IdTermoAbertura;
    }

    public void setIdTermoAbertura(int IdTermoAbertura) {
        this.IdTermoAbertura = IdTermoAbertura;
    }

    @Keep
    public TermoAbertura getTermoAbertura() {
        return termoAbertura;
    }

    @Keep
    public void setTermoAbertura(TermoAbertura termoAbertura) {
        this.termoAbertura = termoAbertura;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 521012512)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMarcoDao() : null;
    }
}