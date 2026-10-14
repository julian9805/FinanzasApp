package com.julian.finanzasapp.transaction;

import com.julian.finanzasapp.account.Cuenta;
import com.julian.finanzasapp.account.CuentaRepository;
import com.julian.finanzasapp.category.Categoria;
import com.julian.finanzasapp.category.CategoriaRepository;
import com.julian.finanzasapp.category.TipoCategoria;
import com.julian.finanzasapp.exception.RecursoNoEncontradoException;
import com.julian.finanzasapp.transaction.dto.TransaccionRequest;
import com.julian.finanzasapp.transaction.dto.TransaccionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final CategoriaRepository categoriaRepository;

    public TransaccionService(TransaccionRepository transaccionRepository,
                              CuentaRepository cuentaRepository,
                              CategoriaRepository categoriaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<TransaccionResponse> listarPorUsuario(Long usuarioId) {
        return transaccionRepository.findByCuentaUsuarioIdOrderByFechaDesc(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Transactional
    public TransaccionResponse crear(TransaccionRequest request, Long usuarioId) {
        Cuenta cuenta = cuentaRepository.findByIdAndUsuarioId(request.getCuentaId(), usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta no encontrada con id: " + request.getCuentaId()));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(request.getCategoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Categoría no encontrada con id: " + request.getCategoriaId()));

        Transaccion transaccion = new Transaccion();
        transaccion.setDescripcion(request.getDescripcion());
        transaccion.setMonto(request.getMonto());
        transaccion.setFecha(request.getFecha());
        transaccion.setCuenta(cuenta);
        transaccion.setCategoria(categoria);

        aplicarAlSaldo(cuenta, categoria.getTipo(), request.getMonto());

        return convertirAResponse(transaccionRepository.save(transaccion));
    }

    @Transactional
    public void eliminar(Long id, Long usuarioId) {
        Transaccion transaccion = transaccionRepository.findByIdAndCuentaUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Transacción no encontrada con id: " + id));

        Cuenta cuenta = transaccion.getCuenta();
        TipoCategoria tipoOriginal = transaccion.getCategoria().getTipo();
        TipoCategoria tipoInverso = tipoOriginal == TipoCategoria.INGRESO
                ? TipoCategoria.GASTO
                : TipoCategoria.INGRESO;
        aplicarAlSaldo(cuenta, tipoInverso, transaccion.getMonto());

        transaccionRepository.delete(transaccion);
    }

    private void aplicarAlSaldo(Cuenta cuenta, TipoCategoria tipo, BigDecimal monto) {
        BigDecimal nuevoSaldo = tipo == TipoCategoria.INGRESO
                ? cuenta.getSaldo().add(monto)
                : cuenta.getSaldo().subtract(monto);
        cuenta.setSaldo(nuevoSaldo);
        cuentaRepository.save(cuenta);
    }

    private TransaccionResponse convertirAResponse(Transaccion t) {
        return new TransaccionResponse(
                t.getId(),
                t.getDescripcion(),
                t.getMonto(),
                t.getFecha(),
                t.getCategoria().getTipo(),
                t.getCuenta().getId(),
                t.getCuenta().getNombre(),
                t.getCategoria().getId(),
                t.getCategoria().getNombre()
        );
    }
}
