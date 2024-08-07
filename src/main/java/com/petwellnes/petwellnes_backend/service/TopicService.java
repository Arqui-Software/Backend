package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.entity.Topic;

import java.util.List;

public interface TopicService {
    Topic createTopic(Topic topic);
    Topic getTopicById(Long id);
    List<Topic> getAllTopics();
    Topic updateTopic(Long id, Topic topic);
    void deleteTopic(Long id);
}
