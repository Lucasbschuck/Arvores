
package arvoreavl;
/**
 *
 * @author lucas.schuck
 */
import java.util.Scanner;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

 
public class ArvoreAVL {
    No raiz;
    public ArvoreAVL(){
        this.raiz = null;
    }
class No {
int valor;
No direita;
No esquerda;
int altura;
public No(int valor){
    this.valor = valor;
    this.direita = null;
    this.esquerda = null;
    this.altura = 1;
}
public int getValor(){return this.valor;}
public No getEsquerda(){return esquerda;}
public No getDireita(){return direita;}
public int getAltura(){return this.altura;}
}


    public void inserir(int valor){
        raiz = inserirRecursivo(raiz,valor);
        raiz = balancear(raiz);
    }
    No inserirRecursivo(No raiz, int valor) {
        if (raiz == null) {
            No no = new No(valor);
            raiz = no;
            return raiz;
        }
        if (valor < raiz.valor) {
            raiz.esquerda = inserirRecursivo(raiz.esquerda, valor);
        }if (valor >= raiz.valor) {
            raiz.direita = inserirRecursivo(raiz.direita, valor);
        }
        raiz.altura = atualizarAltura(raiz);
        raiz = balancear(raiz);
        return raiz;
    }
    void PreOrdem(No raiz) {
        if (raiz != null) {
            System.out.println(raiz.valor + " ");
            PreOrdem(raiz.esquerda);
            PreOrdem(raiz.direita);
        }
    }
    void InOrdem(No raiz) {
        if (raiz != null) {
            InOrdem(raiz.esquerda);
            System.out.println(raiz.valor + " ");
            InOrdem(raiz.direita);
        }
    }
    void PosOrdem(No raiz) {
        if (raiz != null) {
            PosOrdem(raiz.esquerda);
            PosOrdem(raiz.direita);
            System.out.println(raiz.valor + " ");
        }
    }
    void removerMaior(){
        if (this.raiz.esquerda == null && raiz.direita == null){this.raiz=null;return;}
        if(this.raiz.direita == null){raiz = raiz.esquerda;return;}
        raiz = removerMaiorREC(raiz);
    }
    No removerMaiorREC(No raiz) {
        if (raiz == null) {
        return null;
        }
        
        if (raiz.direita == null) {
            if (raiz.esquerda!= null){
            raiz.esquerda.altura = atualizarAltura(raiz.esquerda);
            raiz.esquerda = balancear(raiz.esquerda);}
            return raiz.esquerda;
        }
        raiz.direita = removerMaiorREC(raiz.direita);
        raiz.altura = atualizarAltura(raiz);
        raiz = balancear(raiz);
        return raiz;
    }   
    void removerMenor(){
        if (this.raiz.esquerda == null && raiz.direita == null){this.raiz=null;return;}
        if(this.raiz.esquerda == null){raiz = raiz.direita;return;}
        raiz = removerMenorREC(raiz);
    }
    No removerMenorREC(No raiz) {
        if (raiz == null) {
        return null;
        }
        
        if (raiz.esquerda == null) {
            if (raiz.esquerda!= null){
            raiz.direita.altura = atualizarAltura(raiz.direita);
            raiz.direita = balancear(raiz.direita);}
            return raiz.direita;
        }
        raiz.esquerda = removerMenorREC(raiz.esquerda);
        raiz.altura = atualizarAltura(raiz);
        raiz = balancear(raiz);
        return raiz;
    }
    No RetornarMaior(No raiz){
        if (raiz.direita == null) {
            return raiz;
        }
        return RetornarMaior(raiz.direita);
    }
    No RetornarMenor(No raiz){
        if (raiz.esquerda == null) {
            return raiz;
        }
        return RetornarMenor(raiz.esquerda);
    }
    void removerN(int N){
    raiz = removerNREC(raiz,N);
    raiz = balancear(raiz);
    } 
    No removerNREC(No raiz, int N) {    
        if (raiz == null) {
            System.out.println("Elemento não encontrado");
        }

        if (N < raiz.valor) {
            raiz.esquerda = removerNREC(raiz.esquerda, N);
        } else if (N > raiz.valor) {
            raiz.direita = removerNREC(raiz.direita, N);
        } else if(N == raiz.valor){
            if(raiz.esquerda == null && raiz.direita == null){
            return null;
            }
            else if (raiz.direita == null){
                No noRemovido = RetornarMaior(raiz.esquerda);
                raiz.esquerda = removerMaiorREC(raiz.esquerda);
                raiz.valor = noRemovido.valor;
                return raiz;
            }
            else{
                No noRemovido = RetornarMenor(raiz.direita);
                raiz.direita = removerMenorREC(raiz.direita);
                raiz.valor = noRemovido.valor;
                return raiz;
           
            }
        }
        raiz.altura = atualizarAltura(raiz);
        raiz = balancear(raiz);
        return raiz;
    }


    int atualizarAltura(No raiz){
        if(raiz == null){return 0;}
        if (raiz.direita == null && raiz.esquerda == null){return 1;}
        if(raiz.direita == null){
            return raiz.esquerda.altura + 1;
        }
        if (raiz.esquerda == null){
            return raiz.direita.altura+1;
        }
        return(raiz.esquerda.altura > raiz.direita.altura) ? raiz.esquerda.altura+1 : raiz.direita.altura+1;
    
    }

    int fatorBalanceamento(No raiz) {
    if (raiz == null) {
        return 0;
    }
    int alturaEsquerda = (raiz.esquerda != null) ? raiz.esquerda.altura : 0;
    int alturaDireita = (raiz.direita != null) ? raiz.direita.altura : 0;
    return alturaEsquerda - alturaDireita;
}

    
    No balancear(No raiz){
    if (raiz==null){return raiz;}
    if (raiz.esquerda == null && raiz.direita == null){return raiz;}
    int fatorRaiz = fatorBalanceamento(raiz);
    if(fatorRaiz == 0 || fatorRaiz == 1 || fatorRaiz == -1){return raiz;}
    if(fatorRaiz == 2){
        if (fatorBalanceamento(raiz.esquerda)== -1){raiz.esquerda = rotacaoEsquerda(raiz.esquerda);}
        raiz = rotacaoDireita(raiz);
    }
    else if(fatorRaiz == -2){
        if (fatorBalanceamento(raiz.direita) == 1){raiz.direita = rotacaoDireita(raiz.direita);}
        raiz = rotacaoEsquerda(raiz);
    }
    return raiz;
    }
    No rotacaoEsquerda(No raiz) {
    No novaRaiz = raiz.direita;
    raiz.direita = novaRaiz.esquerda;
    novaRaiz.esquerda = raiz;
    raiz.altura = atualizarAltura(raiz);    
    novaRaiz.altura = atualizarAltura(novaRaiz);
    return novaRaiz;
}

No rotacaoDireita(No raiz) {
    No novaRaiz = raiz.esquerda;
    raiz.esquerda = novaRaiz.direita;
    novaRaiz.direita = raiz;
    raiz.altura = atualizarAltura(raiz);
    novaRaiz.altura = atualizarAltura(novaRaiz);
    raiz = novaRaiz;
    return raiz;
}
public static void visualizeBinaryTree(No root) {
    // Create a new graph
    Graph graph = new SingleGraph("Binary Tree");

    graph.setAttribute("ui.stylesheet",
            "node { size: 30px; fill-color: #3498db, #2980b9; text-size: 14; text-color: #ffffff; text-style: bold; }" +
            "edge { fill-color: #7f8c8d; size: 2px; }");

    // Add nodes and edges to the graph
    addNodesAndEdges(graph, root, null, "");

    // Set layout options (optional)
    graph.setAttribute("ui.quality");
    graph.setAttribute("ui.antialias");

    // Display the graph
    graph.display();
}

private static void addNodesAndEdges(Graph graph, No raiz, org.graphstream.graph.Node parentNode, String parentEdgeId) {
    if (raiz == null) {
        return;
    }

    // Add the current node to the graph with a unique identifier
    String nodeId = "Node_" + System.identityHashCode(raiz); // Use a unique identifier
    org.graphstream.graph.Node currentNode = graph.addNode(nodeId);
    currentNode.setAttribute("ui.label", String.valueOf(raiz.valor));
    if (parentNode == null) {
        currentNode.setAttribute("ui.style", "fill-color: #ff0000;"); // Set to green, you can change the color code
    }

    // Add an edge from the parent node to the current node
    if (parentNode != null) {
        String edgeId = parentEdgeId + System.identityHashCode(raiz);;
        graph.addEdge(edgeId, parentNode.getId(), currentNode.getId()); // Use node IDs
    }

    // Recursively add nodes and edges for the left and right children
    addNodesAndEdges(graph, raiz.esquerda, currentNode, "L");
    addNodesAndEdges(graph, raiz.direita, currentNode, "R");
}


    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        ArvoreAVL arvore = new ArvoreAVL();
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("---------------------------------------------");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Printar em Pre-ordem");
            System.out.println("2 - Printar em Inordem");
            System.out.println("3 - Printar em Pos-ordem");
            System.out.println("4 - Remover maior elemento");
            System.out.println("5 - Remover menor elemento");
            System.out.println("6 - Remover elemento N");
            System.out.println("7 - Inserir elemento Manualmente");
            System.out.println("8 - Inserir elementos Randons");
            System.out.println("9- Remover elementos");
            System.out.println("0 - Sair");
            System.out.println("---------------------------------------------");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    
                    System.out.println("modo Pre-ordem:");
                    arvore.PreOrdem(arvore.raiz );
                    break;
                case 2:
                    System.out.println("modo Inordem:");
                    arvore.InOrdem(arvore.raiz );
                    break;
                case 3:
                    System.out.println("modo Pos-ordem:");
                    arvore.PosOrdem(arvore.raiz );
                    break;
                case 4:
                    arvore.removerMaior();
                    System.out.println("Arvore apos a remocao:");
                    arvore.InOrdem(arvore.raiz);
                    break;
                case 5:
                    arvore.removerMenor();
                    System.out.println("Arvore apos a remocao:");
                    arvore.InOrdem(arvore.raiz);
                    break;
                case 6:
                    System.out.println("Digite N para ser removido:");
                    int valorRemover = scanner.nextInt();
                    arvore.removerN(valorRemover);
                    System.out.println("Arvore apos a remocao de N " + valorRemover + ":" );
                    arvore.InOrdem(arvore.raiz);
                    break;
                case 7:
                    System.out.println("Digite valor para ser Inserido:");
                    int valorInserir = scanner.nextInt();
                    arvore.inserir(valorInserir);
                    System.out.println("Arvore apos a inserção de " + valorInserir + ":" );
                    arvore.InOrdem(arvore.raiz);
                    break;
                case 8:
                    Random random = new Random();
                    System.out.println("Digite quantos valores deseja inserir:");
                    int valor = scanner.nextInt();
                    for (int i = 0; i < valor; i++) {
                        int novoValor = ThreadLocalRandom.current().nextInt(valor);
                        System.out.println(novoValor);
                        arvore.inserir(novoValor);
                        }
                        break;
                case 9:
                    System.out.println("Digite quantos valores deseja remover:");
                    escolha = scanner.nextInt();
                    for (int i = 0; i < escolha; i++) {
                        arvore.removerMaior();
                        }
                    break;
                case 0:
                    System.out.println("Saindo." );
                    System.exit(0);
                    break;
                
            }
            visualizeBinaryTree(arvore.raiz);
        } while (escolha != 0);

        scanner.close();
    }
}