package com.hubstaff.jobs.jobuploadDb.messaging;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

@Service
public class JobsUploadService{
	private static final Logger LOG = LoggerFactory.getLogger(JobsUploadService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Value("${kafka.topic.uploadjobs-test}")
//    private String kafkaTopic;

    public void send() {
         for(int i = 0; i < 100; i++){
            System. out.println(i);
//            Properties props = new Properties();
//            props.put("key.serializer", StringSerializer.class.getName());
//            props.put("value.serializer", JsonSerializer.class.getName());
//            Producer<String, String> producer = new KafkaProducer<String, String>(props);
//            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("uploadjobs-test", "test message - " + i );
//            producer.send(producerRecord);
            kafkaTemplate.send("uploadjobs-test", "{\\\"hi\\\":\\\"hello\\\"}" );
            
        }

    }
}