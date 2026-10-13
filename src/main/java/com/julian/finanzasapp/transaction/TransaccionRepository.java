package com.julian.finanzasapp.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByCuentaUsuarioIdOrderByFechaDesc(Long usuarioId);
}
