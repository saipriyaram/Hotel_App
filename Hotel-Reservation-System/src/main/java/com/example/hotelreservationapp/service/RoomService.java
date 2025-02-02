package com.example.hotelreservationapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.hotelreservationapp.dao.RoomRepository;
import com.example.hotelreservationapp.entity.Room;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> getRoomByRoomNumber(int roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber); // Corrected to return Optional<Room>
    }

    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public boolean saveOrUpdateRoom(Room room) {
        try {
            roomRepository.save(room);
            return true;
        } catch (DataIntegrityViolationException e) {
            // Log the error message if necessary
            return false;
        }
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
