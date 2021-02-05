package ucab.dsw.comando.Sugerencias;

import ucab.dsw.accesodatos.DaoEstudio;
import ucab.dsw.comando.ComandoBase;
import ucab.dsw.fabrica.Fabrica;
import ucab.dsw.response.EstudiosResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListarEstudiosAnalistaComando extends ComandoBase {

    public long id;
    public JsonArrayBuilder estudios = Json.createArrayBuilder();

    public ListarEstudiosAnalistaComando(long id) {
        this.id = id;
    }

    @Override
    public void execute() throws Exception {

        DaoEstudio daoEstudio = Fabrica.crear(DaoEstudio.class);
        ucab.dsw.servicio.SugerenciasServicio servicio = new ucab.dsw.servicio.SugerenciasServicio();
        JsonObject estudioJson;

        List<Object[]> listaEstudios = daoEstudio.listarEstudiosAnalista(id);

        List<EstudiosResponse> listaEstudiosAnalista = new ArrayList<>(listaEstudios.size());

        for (Object[] est: listaEstudios){

            listaEstudiosAnalista.add(new EstudiosResponse((long)est[0], (String)est[1], (String)est[2], (String)est[3], servicio.devolverFecha((Date)est[4]), servicio.devolverFecha((Date)est[5]), (String)est[6], (String)est[7]));
        }

        for (EstudiosResponse obj : listaEstudiosAnalista) {

            if(obj.getObservacionesEstudio() != null) {

                estudioJson = Json.createObjectBuilder().add("id", obj.getIdEstudio())
                        .add("nombre", obj.getNombreEstudio())
                        .add("tipoInstrumento", obj.getTipoInstrumentoEstudio())
                        .add("observaciones", obj.getObservacionesEstudio())
                        .add("fechaInicio", obj.getFechaInicioEstudio())
                        .add("fechaFin", obj.getFechaFinEstudio())
                        .add("estado", obj.getEstadoEstudio())
                        .add("estatus", obj.getEstatusEstudio()).build();

                estudios.add(estudioJson);

            } else {

                estudioJson = Json.createObjectBuilder().add("id", obj.getIdEstudio())
                        .add("nombre", obj.getNombreEstudio())
                        .add("tipoInstrumento", obj.getTipoInstrumentoEstudio())
                        .add("observaciones", "")
                        .add("fechaInicio", obj.getFechaInicioEstudio())
                        .add("fechaFin", obj.getFechaFinEstudio())
                        .add("estado", obj.getEstadoEstudio())
                        .add("estatus", obj.getEstatusEstudio()).build();

                estudios.add(estudioJson);
            }

        }

    }

    @Override
    public JsonObject getResult() {

        JsonObject resultado = Json.createObjectBuilder().add("mensaje","Todos los estudios del analista")
                .add("estado",200)
                .add("Estudios", estudios).build();

        return resultado;
    }
}
