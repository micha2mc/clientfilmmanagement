package com.zakado.zkd.clientfilmmanagement.repository;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepo extends JpaRepository<Actor, Integer> {
}
