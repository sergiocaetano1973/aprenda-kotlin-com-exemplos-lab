enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO }

data class ConteudoEducacional(
    val nome: String,
    val tipo: TipoConteudo,
    val nivel: Nivel
) {
    // Função para calcular a duração conforme o nível e tipo de conteúdo
    fun calcularDuracao(): Int {
        return when (nivel) {
            Nivel.BASICO -> when (tipo) {
                TipoConteudo.KOTLIN -> 30
                TipoConteudo.JAVA -> 30
                TipoConteudo.INGLES -> 30
            }
            Nivel.INTERMEDIARIO -> when (tipo) {
                TipoConteudo.KOTLIN -> 60
                TipoConteudo.JAVA -> 60
                TipoConteudo.INGLES -> 60
            }
            Nivel.AVANCADO -> when (tipo) {
                TipoConteudo.KOTLIN -> 90
                TipoConteudo.JAVA -> 90
                TipoConteudo.INGLES -> 90
            }
        }
    }
}

// Definindo os tipos de conteúdo possíveis
enum class TipoConteudo {
    KOTLIN,
    JAVA,
    INGLES
}

data class Usuario(val id: Int, val name: String, var email: String) {
    init {
        require(isValidEmail(email)) { "O e-mail fornecido é inválido." }
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()
        return regex.matches(email)
    }

    // Função para retornar o ID formatado com 3 dígitos
    fun getFormattedId(): String {
        return String.format("%03d", id)  // Formata o id como 3 dígitos
    }
}

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {
    val inscritos = mutableListOf<Usuario>()
    
    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
    }

    fun getQuantosInscritos() = inscritos.size

    fun listarMatriculados() {
        inscritos.forEach { println("ID: ${it.getFormattedId()}, Nome: ${it.name}") }
    }

    // Função para buscar um usuário pelo nome
    fun buscarUsuarioPorNome(nome: String): Usuario? {
        return inscritos.find { it.name.equals(nome, ignoreCase = true) }
    }

    // Função para buscar um usuário pelo ID
    fun buscarUsuarioPorId(id: Int): Usuario? {
        return inscritos.find { it.id == id }
    }
    // Função total de alunos
    fun totalDeAulunosMatriculados(): Int {
        return inscritos.size
    }
}

fun main() {
    // Lista de usuários com e-mails válidos e inválidos (agora com nomes reais)
    val usuarios = listOf(
        "joao.silva@dominio.com" to "João Silva",
        "maria.oliveira@dominio.com" to "Maria Oliveira",
        "carlos.santos@dominio.com" to "Carlos Santos",
        "ana.souza@dominio.com" to "Ana Souza",
        "pedro.costa@dominio.com" to "Pedro Costa",
        "lucas.pereira@dominio.com" to "Lucas Pereira",
        "juliana.ribeiro@dominio.com" to "Juliana Ribeiro",
        "fernanda.lima@dominio.com" to "Fernanda Lima",
        "roberto.ferreira@dominio.com" to "Roberto Ferreira",
        "larissa.gomes@dominio.com" to "Larissa Gomes",
        "renato.martins@dominio.com" to "Renato Martins",
        "patricia.santos@dominio.com" to "Patricia Santos",
        "jorge.almeida@dominio.com" to "Jorge Almeida",
        "claudia.pinto@dominio.com" to "Claudia Pinto",
        "felipe.costa@dominio.com" to "Felipe Costa",
        "ricardo.barbosa@dominio.com" to "Ricardo Barbosa",
        "vanessa.rocha@dominio.com" to "Vanessa Rocha",
        "tiago.lima@dominio.com" to "Tiago Lima",
        "bianca.silva@dominio.com" to "Bianca Silva",
        "gustavo.fernandes@dominio.com" to "Gustavo Fernandes",
        "rafael.souza@dominio.com" to "Rafael Souza",
        // E-mails inválidos
        "eduardo@dominio" to "Eduardo Oliveira",
        "isabela.ramos@dominio@com" to "Isabela Ramos"
    )

    // Filtrando os usuários com e-mails válidos
    val usuariosValidos = usuarios.filter { (email, _) ->
        try {
            // Tenta criar um usuário para verificar se o e-mail é válido
            Usuario(id = 0, name = "Test", email = email)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }.mapIndexed { index, (email, name) -> 
        Usuario(id = index + 1, name = name, email = email)
    }

    // Criando alguns cursos e matrículas
    val conteudos = listOf(
        ConteudoEducacional("Kotlin", TipoConteudo.KOTLIN, Nivel.BASICO),
        ConteudoEducacional("Java", TipoConteudo.JAVA, Nivel.BASICO),
        ConteudoEducacional("Inglês", TipoConteudo.INGLES, Nivel.BASICO),
        ConteudoEducacional("Kotlin", TipoConteudo.KOTLIN, Nivel.INTERMEDIARIO),
        ConteudoEducacional("Java", TipoConteudo.JAVA, Nivel.INTERMEDIARIO),
        ConteudoEducacional("Inglês", TipoConteudo.INGLES, Nivel.INTERMEDIARIO),
        ConteudoEducacional("Kotlin", TipoConteudo.KOTLIN, Nivel.AVANCADO),
        ConteudoEducacional("Java", TipoConteudo.JAVA, Nivel.AVANCADO),
        ConteudoEducacional("Inglês", TipoConteudo.INGLES, Nivel.AVANCADO)
    )

    val formacao = Formacao("Formação Completa", conteudos)

    // Matriculando os usuários válidos
    usuariosValidos.forEach {
        formacao.matricular(it)
    }
    
    // Exibindo o total de alunos matriculados
    println("Total de alunos matriculados: ${formacao.totalDeAulunosMatriculados()}")
    println("<----------------------------------------------------------------------------------------------------------------->")

    // Exibindo os dados da formação
    formacao.conteudos.forEach { curso ->
        println("${curso.nome} (${curso.tipo}) - Nível: ${curso.nivel} - Duração: ${curso.calcularDuracao()} minutos")
        println("<----------------------------------------------------------------------------------------------------------------->")
        println("Quantidade de usuários matriculados: ${formacao.getQuantosInscritos()}")
        println("Matrículas por curso: ")
        formacao.listarMatriculados()
        println("<----------------------------------------------------------------------------------------------------------------->")
    }

    // Buscando um usuário pelo nome
    val nomeBusca = "João Silva"
    val usuarioPorNome = formacao.buscarUsuarioPorNome(nomeBusca)
    if (usuarioPorNome != null) {
        println("Usuário encontrado pelo nome '$nomeBusca': ID: ${usuarioPorNome.getFormattedId()}, Nome: ${usuarioPorNome.name}")
    } else {
        println("Usuário com nome '$nomeBusca' não encontrado.")
    }

    // Buscando um usuário pelo ID
    val idBusca = 5
    val usuarioPorId = formacao.buscarUsuarioPorId(idBusca)
    if (usuarioPorId != null) {
        println("Usuário encontrado pelo ID '$idBusca': ID: ${usuarioPorId.getFormattedId()}, Nome: ${usuarioPorId.name}")
    } else {
        println("Usuário com ID '$idBusca' não encontrado.")
    }
}
