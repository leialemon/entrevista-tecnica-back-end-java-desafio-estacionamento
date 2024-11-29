package br.com.ada.estacionamento.carros;

import br.com.ada.estacionamento.vagas.Vaga;
import br.com.ada.estacionamento.vagas.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository carroRepository;
    private final VagaRepository vagaRepository;

    public CarroService(CarroRepository carroRepository, VagaRepository vagaRepository){
        this.carroRepository = carroRepository;
        this.vagaRepository = vagaRepository;
    }

    public void cadastrarCarro(Carro carro){
        boolean estacionamentoLotado = checarLotacao();
        if (estacionamentoLotado){
            throw new RuntimeException("O estacionamento está lotado!");
        }
        carroRepository.save(carro);
    }

    public void estacionar(Carro carro, Vaga vaga){
        if (vaga.isOcupada()){
            throw new RuntimeException("A vaga já está ocupada!");
        } else {
            carro.setVaga(vaga);
            vaga.setCarro(carro);
            vaga.setOcupada(true);
            carroRepository.save(carro);
        }
    }


    public boolean checarLotacao(){
        boolean estacionamentoLotado = true;
        Iterable<Vaga> vagas = vagaRepository.findAll();
        for (Vaga vaga : vagas){
            if (!vaga.isOcupada()){
                estacionamentoLotado = false;
                return estacionamentoLotado;
            }
        }
        return estacionamentoLotado;
    }

}
