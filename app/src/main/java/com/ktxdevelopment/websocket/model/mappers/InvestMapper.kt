package com.ktxdevelopment.websocket.model.mappers

import com.ktxdevelopment.websocket.model.dto.Domain
import com.ktxdevelopment.websocket.model.dto.Dto
import com.ktxdevelopment.websocket.model.local.InvestEntity
import com.ktxdevelopment.websocket.model.remote.InvestItem
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import kotlin.experimental.ExperimentalTypeInference

// Injected Mapper

class InvestMapper {

    fun mapToEntity(item: InvestItem) = InvestEntity(
        item.trend,
        item.company,
        item.val1,
        item.val2,
        item.val3,
        item.val4,
        item.rank,
        item.date
    )

    fun mapToDto(item : InvestEntity) = InvestItem(
        item.trend,
        item.company,
        item.val1,
        item.val2,
        item.val3,
        item.val4,
        item.rank,
        item.date
    )


    fun mapToEntity(invests: List<InvestItem>): ArrayList<InvestEntity> {
        val list = arrayListOf<InvestEntity>()
        invests.forEach { list.add(mapToEntity(it)) }
        return list
    }

    fun mapToDto(entities: List<InvestEntity>): ArrayList<InvestItem> {
        val list = arrayListOf<InvestItem>()
        entities.forEach { list.add(mapToDto(it)) }
        return list
    }


    @OptIn(ExperimentalTypeInference::class)
    public inline fun <T, R> Flow<T>.transform(
        @BuilderInference crossinline transform:
        suspend FlowCollector<R>.(value: T) -> Unit
    ): Flow<R> = flow {
        collect { value ->
            return@collect transform(value)
        }
    }

    fun toDomain(flow: Flow<Dto>): Flow<Domain> = flow.transform { value ->
        return@transform emit(value.asDomain())
    }

    fun toDto(flow: Flow<Domain>): Flow<Dto> = flow.transform { value ->
        return@transform emit(value.asDto())
    }

    internal fun <T, R> mapList(flow: Flow<List<T>>, transform: (T) -> R) = flow.map { it.map(transform) }
}