package com.farmaciasperuanas.digital.user.client.repository;

import com.farmaciasperuanas.digital.user.client.entity.MaeEstadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaeEstadoRepository extends JpaRepository<MaeEstadoEntity, String> {
    public List<MaeEstadoEntity> findByCoEstado(String coEstado);
}
