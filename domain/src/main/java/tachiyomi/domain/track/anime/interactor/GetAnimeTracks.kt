package tachiyomi.domain.track.anime.interactor

import kotlinx.coroutines.flow.Flow
import logcat.LogPriority
import tachiyomi.core.util.system.logcat
import tachiyomi.domain.track.anime.model.AnimeTrack
import tachiyomi.domain.track.anime.repository.AnimeTrackRepository

class GetAnimeTracks(
    private val animetrackRepository: AnimeTrackRepository,
) {

    suspend fun awaitOne(id: Long): AnimeTrack? {
        return try {
            animetrackRepository.getTrackByAnimeId(id)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            null
        }
    }

    // SY -->
    suspend fun await(): List<AnimeTrack> {
        return try {
            animetrackRepository.getAnimeTracks()
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyList()
        }
    }

    suspend fun await(animeIds: List<Long>): Map<Long, List<AnimeTrack>> {
        return try {
            animetrackRepository.getTracksByAnimeIds(animeIds)
                .groupBy { it.animeId }
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyMap()
        }
    }
    // SY <--

    suspend fun await(animeId: Long): List<AnimeTrack> {
        return try {
            animetrackRepository.getTracksByAnimeId(animeId)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyList()
        }
    }

    fun subscribe(animeId: Long): Flow<List<AnimeTrack>> {
        return animetrackRepository.getTracksByAnimeIdAsFlow(animeId)
    }
}
