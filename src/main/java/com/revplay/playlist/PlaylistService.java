package com.revplay.playlist;

import com.revplay.song.Song;
import com.revplay.song.SongRepository;
import com.revplay.user.RpUser;
import com.revplay.user.RpUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final RpUserRepository userRepository;

    public Playlist createPlaylist(String name,
                                   String description,
                                   boolean isPublic) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        RpUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = Playlist.builder()
                .name(name)
                .description(description)
                .isPublic(isPublic)
                .owner(user)
                .build();

        return playlistRepository.save(playlist);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) {

        Playlist playlist = validateOwnership(playlistId);

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        int nextPosition =
                playlistSongRepository
                        .findByPlaylistIdOrderByPositionAsc(playlistId)
                        .size();

        PlaylistSong playlistSong = PlaylistSong.builder()
                .playlist(playlist)
                .song(song)
                .position(nextPosition)
                .build();

        playlistSongRepository.save(playlistSong);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) {

        validateOwnership(playlistId);

        playlistSongRepository
                .deleteByPlaylistIdAndSongId(playlistId, songId);
    }

    public List<PlaylistSong> getPlaylistSongs(Long playlistId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        if (!playlist.isPublic() &&
                !playlist.getOwner().getUsername().equals(username)) {

            throw new RuntimeException("Private playlist access denied");
        }

        return playlistSongRepository
                .findByPlaylistIdOrderByPositionAsc(playlistId);
    }

    private Playlist validateOwnership(Long playlistId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        if (!playlist.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("You are not the owner of this playlist");
        }

        return playlist;
    }
}