package com.exashare.Exashare;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.ResenaHerramienta;
import com.exashare.Exashare.model.ResenaUsuario;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.repository.ArriendoRepository;
import com.exashare.Exashare.repository.HerramientaRepository;
import com.exashare.Exashare.repository.HerramientasRepository;
import com.exashare.Exashare.repository.MembresiaRepository;
import com.exashare.Exashare.repository.ResenaHerramientaRepository;
import com.exashare.Exashare.repository.ResenaUsuarioRepository;
import com.exashare.Exashare.repository.ResenasRepository;
import com.exashare.Exashare.repository.TipoUsuarioRepository;
import com.exashare.Exashare.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private ArriendoRepository arriendoRepository;

    @Autowired
    private HerramientaRepository herramientaRepository;

    @Autowired
    private HerramientasRepository herramientasRepository;

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private ResenaHerramientaRepository resenaHerramientaRepository;

    @Autowired
    private ResenasRepository resenasRepository;

    @Autowired 
    private ResenaUsuarioRepository resenaUsuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository; 

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void run(String... args) throws Exception{

        Faker faker = new Faker();
        Random random = new Random();

        // 1. Crear Tipos de Usuario
        if (tipoUsuarioRepository.count() == 0) {
            tipoUsuarioRepository.save(new TipoUsuario(null, "Arrendador", "Usuario que arrienda herramientas"));
            tipoUsuarioRepository.save(new TipoUsuario(null, "Arrendatario", "Usuario que arrienda herramientas de otros")); // Si ya existen tipos de usuario, no los creamos de nuevo
        }
        
        List<TipoUsuario> tiposUsuario = tipoUsuarioRepository.findAll();

        // 2. Crear Membresías
        for (int i = 0; i < 3; i++) {
            Membresia membresia = new Membresia();
            membresia.setTipo("Membresía " + (i+1));
            membresia.setFechaInicio(new Date());
            membresia.setFechaFin(Date.from(LocalDate.now().plusMonths(6).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            membresia.setBeneficios(faker.lorem().sentence(8));
            membresiaRepository.save(membresia);
            }
       
        List<Membresia> membresias = membresiaRepository.findAll();

        // 3. Crear Usuarios
        
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(faker.internet().username());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContraseña(faker.internet().password(8, 16));
            usuario.setDireccion(faker.address().streetAddress());
            usuario.setTelefono(faker.phoneNumber().cellPhone());
            usuario.setIdTipoUsuario(tiposUsuario.get(random.nextInt(tiposUsuario.size())));
            usuario.setIdMembresia(membresias.get(random.nextInt(membresias.size())));
            usuarioRepository.save(usuario);
            }
        
        List<Usuario> usuarios = usuarioRepository.findAll();

        // 4. Crear Herramientas
 
        for (int i = 0; i < 10; i++) {
            Herramienta herramienta = new Herramienta();
            herramienta.setNombre(faker.commerce().productName());
            herramienta.setDescripcion(faker.lorem().sentence(5));
            herramienta.setCategoria(faker.commerce().department());
            herramienta.setEstado(faker.options().option("NUEVO", "USADO"));
            herramienta.setUbicacion(faker.address().city());
            herramienta.setPrecioHerramienta(faker.number().numberBetween(5000, 50000));
            herramienta.setIdUsuario(usuarios.get(random.nextInt(usuarios.size())));
            herramientaRepository.save(herramienta);
            }
        
        List<Herramienta> herramientas = herramientaRepository.findAll();

        // 5. Crear Arriendos y Herramientas puente
        for (int i = 0; i < 5; i++) {
            Arriendo arriendo = new Arriendo();
            // Fecha aleatoria entre hoy y 30 días en el futuro
            LocalDate fechaInicio = LocalDate.now().plusDays(random.nextInt(31));
            arriendo.setFechaInicio(Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            // Fecha de fin aleatoria entre 1 y 30 días después de la fecha de inicio
            int diasAdicionales = random.nextInt(30) + 1;
            LocalDate fechaFin = fechaInicio.plusDays(diasAdicionales);
            arriendo.setFechaFin(Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            // Estado del arriendo basado en la fecha de inicio y fin
            LocalDate hoy = LocalDate.now();
            if (fechaInicio.isAfter(hoy)) {
                arriendo.setEstado("PENDIENTE");
            } else if ((fechaInicio.isEqual(hoy) || fechaInicio.isBefore(hoy)) && fechaFin.isAfter(hoy)) {
                arriendo.setEstado("ACTIVO");
            } else {
                arriendo.setEstado("FINALIZADO");
            }
            arriendo.setPrecioArriendo(faker.number().numberBetween(10000, 50001));
            arriendo = arriendoRepository.save(arriendo);

            // Asociar 1-2 herramientas a cada arriendo
            int cantidadHerramientas = random.nextInt(2) + 1;
            for (int j = 0; j < cantidadHerramientas; j++) {
                Herramientas herramientasPuente = new Herramientas();
                herramientasPuente.setArriendo(arriendo);
                herramientasPuente.setHerramienta(herramientas.get(random.nextInt(herramientas.size())));
                herramientasRepository.save(herramientasPuente);
            }
        }

        // 6. Crear Reseñas de Usuario y asociarlas a usuarios
        for (int i = 0; i < 5; i++) {
            ResenaUsuario resenaUsuario = new ResenaUsuario();
            resenaUsuario.setPuntuacion(faker.number().numberBetween(1, 6));
            resenaUsuario.setComentario(faker.lorem().sentence(8));
            resenaUsuario.setFecha(new Date());
            resenaUsuarioRepository.save(resenaUsuario);

            // Asociar con tabla puente Resenas
            Resenas resenas = new Resenas();
            resenas.setResenaUsuario(resenaUsuario);
            resenas.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            resenasRepository.save(resenas);
        }

        // 7. Crear Reseñas de Herramienta
        for (int i = 0; i < 5; i++) {
            ResenaHerramienta resenaHerramienta = new ResenaHerramienta();
            resenaHerramienta.setPuntuacion(random.nextInt(5) + 1);
            resenaHerramienta.setComentario(faker.lorem().sentence(8));
            resenaHerramienta.setFecha(new Date());
            resenaHerramienta.setIdHerramienta_fk(herramientas.get(random.nextInt(herramientas.size())));
            resenaHerramientaRepository.save(resenaHerramienta);
        }
    }

}
