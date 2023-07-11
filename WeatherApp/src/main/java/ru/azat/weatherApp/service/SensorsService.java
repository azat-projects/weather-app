package ru.azat.weatherApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azat.weatherApp.model.Sensor;
import ru.azat.weatherApp.repository.SensorsRepository;
import ru.azat.weatherApp.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll(){
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id){
        Optional<Sensor> sensor = sensorsRepository.findById(id);
        return sensor.orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        enrichSensor(sensor);
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        return sensorsRepository.findByName(name);
    }

    private void enrichSensor(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
    }
}
