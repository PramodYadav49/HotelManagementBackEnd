import React from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import ApiService from '../../service/ApiService';

const RoomResult = ({ roomSearchResults }) => {
    const navigate = useNavigate(); // Initialize useNavigate hook
    const isAdmin = ApiService.isAdmin();

    console.log('Room Search Results:', roomSearchResults); // Log the entire roomSearchResults array

    return (
        <section className="room-results">
            {roomSearchResults && roomSearchResults.length > 0 ? (
                <div className="room-list">
                    {roomSearchResults.map(room => {
                        console.log('Room Photo URL:', room.roomPhoto); // Log each room's photo URL
                        return (
                            <div key={room.id} className="room-list-item">
                                <img
                                    className="room-list-item-image"
                                    src={room.roomPhoto}
                                    alt={room.roomType}
                                />
                                <div className="room-details">
                                    <h3>{room.roomType}</h3>
                                    <p>Price: ${room.roomPrice} / night</p>
                                    <p>Description: {room.roomDescription}</p>
                                </div>
                                <div className='book-now-div'>
                                    {isAdmin ? (
                                        <button
                                            className="edit-room-button"
                                            onClick={() => navigate(`/admin/edit-room/${room.id}`)}
                                        >
                                            Edit Room
                                        </button>
                                    ) : (
                                        <button
                                            className="book-now-button"
                                            onClick={() => navigate(`/room-details-book/${room.id}`)}
                                        >
                                            View/Book Now
                                        </button>
                                    )}
                                </div>
                            </div>
                        );
                    })}
                </div>
            ) : (
                <p>No rooms available based on your search criteria.</p>
            )}
        </section>
    );
}

export default RoomResult;
