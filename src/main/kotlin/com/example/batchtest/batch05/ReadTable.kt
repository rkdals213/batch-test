package com.example.batchtest.batch05

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Entity
data class ReadTable(
    @EmbeddedId
    val idGroup: IdGroup,

    val createdDateTime: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ReadTable

        return idGroup == other.idGroup
    }

    override fun hashCode(): Int = Objects.hash(idGroup);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(EmbeddedId = $idGroup , createdDateTime = $createdDateTime )"
    }
}

@Embeddable
data class IdGroup(
    @Column
    val soId: String,
    @Column
    val ctrtId: String,
    @Column
    val custId: String,
    @Column
    val pymAcntId: String,
    @Column
    val orderTp: String,
) : Serializable

data class ReadTableDto(
    var id: Long,
    var soId: String,
    var ctrtId: String,
    var custId: String,
    var pymAcntId: String,
    var orderTp: String,
    var createdDateTime: LocalDateTime,
) {

    constructor(id: Long, soId: String, ctrtId: String, custId: String, pymAcntId: String, orderTp: String, createdDateTime: String) : this(
        id, soId, ctrtId, custId, pymAcntId, orderTp, LocalDateTime.parse(createdDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    )
}
