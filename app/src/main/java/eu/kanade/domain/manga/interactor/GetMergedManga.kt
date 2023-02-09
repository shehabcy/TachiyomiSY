package eu.kanade.domain.manga.interactor

import eu.kanade.tachiyomi.util.system.logcat
import kotlinx.coroutines.flow.Flow
import logcat.LogPriority
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.repository.MangaMergeRepository

class GetMergedManga(
    private val mangaMergeRepository: MangaMergeRepository,
) {

    suspend fun await(): List<Manga> {
        return try {
            mangaMergeRepository.getMergedManga()
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            emptyList()
        }
    }

    suspend fun subscribe(): Flow<List<Manga>> {
        return mangaMergeRepository.subscribeMergedManga()
    }
}
