package db.movies.movies.enums

enum class GenresMoviesEnum(var id: Int, var title: String) {
    ACAO(28, "Ação"),
    DRAMA(18, "Drama"),
    FANTASIA(14, "Fantasia"),
    FICCAO(878, "Ficção");

    companion object  {
        fun valor(id: Int?): GenresMoviesEnum = when(id) {
            28 -> ACAO
            18 -> DRAMA
            14 -> FANTASIA
            else -> FICCAO
        }
    }
}