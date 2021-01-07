package ucab.dsw.servicio;

import lombok.extern.java.Log;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.RespuestaDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.response.EncuestaResponse;
import ucab.dsw.response.EstudioEncuestadoResponse;
import ucab.dsw.response.PreguntasResponse;
import ucab.dsw.response.RespuestaPreguntaResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Log
@Path( "/encuesta" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class EncuestaServicio {

    /**
     * Este método permite obtener las preguntas de una encuesta
     * @author Gregg Spinetti y Emanuel Di Cristofaro
     * @param id
     */
    @GET
    @Path("/preguntas/{id}")
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response obtenerPreguntaEncuesta(@PathParam("id") long id) throws Exception {

        DaoPreguntaEncuesta daoPreguntaEncuesta = new DaoPreguntaEncuesta();
        JsonObject dataObject;
        JsonArrayBuilder preguntasArrayJson = Json.createArrayBuilder();

        try {

            List<Object[]> preguntasRespuestas = daoPreguntaEncuesta.listarPreguntas(id);

            List<EncuestaResponse> ResponseListUpdate = new ArrayList<>(preguntasRespuestas.size());

            for (Object[] r : preguntasRespuestas) {

                ResponseListUpdate.add(new EncuestaResponse((long)r[0], (String)r[1], (String)r[2], (long)r[3]));
            }

            for (EncuestaResponse er: ResponseListUpdate){

                JsonObject pregunta = Json.createObjectBuilder()
                        .add("id", er.getIdPreguntaEncuesta())
                        .add("descripcion", er.getDescripcion())
                        .add("tipoPregunta", er.getTipoPregunta())
                        .add("idEstudio", er.getIdPreguntaEstudio()).build();

                preguntasArrayJson.add(pregunta);
            }

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Operacion realizada con éxito")
                    .add("codigo", 200)
                    .add("Preguntas", preguntasArrayJson).build();

            return Response.status(Response.Status.OK).entity(dataObject).build();

        } catch (Exception ex) {

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Error")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 400).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(dataObject).build();
        }

    }

    /**
     * Este método permite obtener las respuestas de las preguntas de una encuesta
     * @author Gregg Spinetti y Emanuel Di Cristofaro
     * @param id
     */
    @GET
    @Path("/respuestas/{id}")
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response obtenerRespuestaEncuesta(@PathParam("id") long id) throws Exception {

        DaoRespuestaPregunta daoRespuestaPregunta = new DaoRespuestaPregunta();
        JsonObject dataObject;
        JsonArrayBuilder respuestasArrayJson = Json.createArrayBuilder();

        try {

            List<Object[]> respuestas = daoRespuestaPregunta.listaRespuestaEncuesta(id);

            List<RespuestaPreguntaResponse> ResponseListUpdate = new ArrayList<>(respuestas.size());

            for (Object[] r : respuestas) {

                ResponseListUpdate.add(new RespuestaPreguntaResponse((Long)r[0], (String)r[1]));
            }

            for (RespuestaPreguntaResponse rpr: ResponseListUpdate){

                JsonObject pregunta = Json.createObjectBuilder()
                        .add("fkPregunta", rpr.getFkPregunta())
                        .add("pregunta", rpr.getPregunta()).build();

                respuestasArrayJson.add(pregunta);
            }

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Operacion realizada con éxito")
                    .add("codigo", 200)
                    .add("Respuestas", respuestasArrayJson).build();

            return Response.status(Response.Status.OK).entity(dataObject).build();

        } catch (Exception ex) {

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Error")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 400).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(dataObject).build();
        }
    }

    /**
     * Este método permite obtener los encuestados
     * @author Gregg Spinetti y Emanuel Di Cristofaro
     * @param id
     */
    @GET
    @Path("/listar/encuestados/{id}")
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response getAllByRespuesta(@PathParam("id") long id) throws Exception {

        DaoUsuario daoUsuario = new DaoUsuario();
        JsonObject dataObject;
        JsonArrayBuilder encuestadosArrayJson = Json.createArrayBuilder();

        try {

            List<Object[]> estudioUsuarioResponseList = daoUsuario.listarEncuestados(id);

            List<EstudioEncuestadoResponse> estudioUsuarioResponseListUpdate = new ArrayList<>(estudioUsuarioResponseList.size());

            for (Object[] r : estudioUsuarioResponseList) {
                estudioUsuarioResponseListUpdate.add(new EstudioEncuestadoResponse((Long)r[1], (String)r[2], (String)r[3]));
            }

            for (EstudioEncuestadoResponse eer: estudioUsuarioResponseListUpdate){

                JsonObject usuario = Json.createObjectBuilder()
                        .add("id", eer.getIdUsuario())
                        .add("correo", eer.getCorreo())
                        .add("nombreUsuario", eer.getNombreUsuario()).build();

                encuestadosArrayJson.add(usuario);
            }

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Operacion realizada con éxito")
                    .add("codigo", 200)
                    .add("Encuestados", encuestadosArrayJson).build();

            return Response.status(Response.Status.OK).entity(dataObject).build();

        } catch (Exception ex) {

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Error")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 400).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(dataObject).build();
        }

    }

    /**
     * Este método permite registrar una lista de respuestas en una encuesta
     * @author Gregg Spinetti y Emanuel Di Cristofaro
     * @param respuestas la lista de respuestas seleccionada en la encuesta
     */
    @POST
    @Path( "/responder" )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response addRespuesta(List<RespuestaDto> respuestas) throws PruebaExcepcion {

        JsonObject dataObject;
        RespuestaDto resultado = new RespuestaDto();

        try
        {
            DaoRespuesta dao = new DaoRespuesta();
            DaoPreguntaEstudio daoPreguntaEstudio = new DaoPreguntaEstudio();
            DaoUsuario daoUsuario = new DaoUsuario();
            DaoPreguntaEncuesta daoPreguntaEncuesta = new DaoPreguntaEncuesta();

            for (RespuestaDto respuestaDto: respuestas) {
                Respuesta respuesta = new Respuesta();
                respuesta.set_estatus(respuestaDto.getEstatus());

                respuesta.set_escala(respuestaDto.getEscala());
                respuesta.set_respuestaAbierta(respuestaDto.getRespuestaAbierta());
                respuesta.set_respuestaMultiple(respuestaDto.getRespuestaMultiple());
                respuesta.set_respuestaSimple(respuestaDto.getRespuestaSimple());
                respuesta.set_verdaderoFalso(respuestaDto.getVerdaderoFalso());

                PreguntaEstudio preguntaEstudio = daoPreguntaEstudio.find(respuestaDto.getPreguntaEstudioDto().getId(), PreguntaEstudio.class);
                Usuario usuario = daoUsuario.find(respuestaDto.getUsuarioDto().getId(), Usuario.class);
                PreguntaEncuesta preguntaEncuesta = daoPreguntaEncuesta.find(preguntaEstudio.get_preguntaEncuesta().get_id(), PreguntaEncuesta.class);
                respuesta.set_usuario(usuario);
                preguntaEstudio.set_preguntaEncuesta(preguntaEncuesta);
                respuesta.set_preguntaEstudio(preguntaEstudio);

                Respuesta resul = dao.insert(respuesta);
                resultado.setId(resul.get_id());

            }

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Operacion realizada con éxito")
                    .add("codigo", 200).build();

            return Response.status(Response.Status.OK).entity(dataObject).build();

        } catch (PruebaExcepcion ex) {

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Error")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 400).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(dataObject).build();

        } catch (NullPointerException ex) {

            dataObject = Json.createObjectBuilder()
                    .add("estado", "Error")
                    .add("excepcion", "No se ha podido ejecutar la operación " + ex.getMessage())
                    .add("codigo", 400).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(dataObject).build();

        }
    }
}

