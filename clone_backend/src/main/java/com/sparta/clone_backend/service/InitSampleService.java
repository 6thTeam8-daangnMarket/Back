package com.sparta.clone_backend.service;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sparta.clone_backend.model.Sample;
import com.sparta.clone_backend.repository.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitSampleService implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(InitSampleService.class);

    private final String FILE_INIT_SAMPLE = "config/init_sample.json";

    private final SampleRepository sampleRepository;

    public InitSampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Sample> sampleList = new ArrayList<>();
        sampleList = getInitSamplesFromFile();
        if (sampleList == null || sampleList.size() == 0) {
            throw new IllegalArgumentException("no data in init data file.");
        }

        for (Sample sample : sampleList) {
            sampleRepository.save(sample);
        }
    }

    private List<Sample> getInitSamplesFromFile() throws IOException {
        List<Sample> sampleList = new ArrayList<>();
        try (InputStream is = getStreamFromResource(FILE_INIT_SAMPLE)) {
            JsonNode sampleNode = getSampleNode(is);
            sampleList = getSampleListFromNode(sampleNode);
        }

        return sampleList;
    }

    private InputStream getStreamFromResource(String fileLocation) {
        ClassLoader classLoader = InitSampleService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileLocation);

        if (inputStream == null) {
            throw new IllegalArgumentException("init data file \"" + fileLocation + "\" not found.");
        } else {
            return inputStream;
        }
    }

    private JsonNode getSampleNode(InputStream is) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode sampleNode = null;
        try (DataInputStream dis = new DataInputStream(is)) {
            sampleNode = objectMapper.readTree(dis).path("sample");
        } catch (IOException io) {
            io.printStackTrace();
        }
        return sampleNode;
    }

    private List<Sample> getSampleListFromNode(JsonNode sampleNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Sample> sampleList = objectMapper.convertValue(sampleNode, new TypeReference<List<Sample>>() {
        }).stream().collect(Collectors.toList());
        return sampleList;
    }
}