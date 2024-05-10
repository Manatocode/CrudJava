package com.mycompany.crudprojeto;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import com.google.firebase.cloud.FirestoreClient;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Pessoa {
    
    CollectionReference reference;
     
    static Firestore db;
    
    public static boolean salvarPessoa(String coleccion, String documento,
                Map<String, Object> data) {
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Salvo com sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
        return false;
        
    
        }
    
    public static boolean atualizarPessoa(String coleccion, String documento,
                Map<String, Object> data) {
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Atualizado com sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
        return false;
        
    
        }
    
    public static boolean deletarPessoa(String coleccion, String documento) {
        
        db = FirestoreClient.getFirestore();
        
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Deletado com sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
        return false;
        }
       
    public static void carregarTabelaPessoa(JTable table){
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Autor");
        model.addColumn("Email");
        model.addColumn("Lançamento");
        
        try {
            CollectionReference pessoa = Conexao.db.collection("Pessoa");
            ApiFuture<QuerySnapshot> querySnap = pessoa.get();
            
            for(DocumentSnapshot document: querySnap.get().getDocuments()){
                model.addRow(new Object[]{
                    document.getId(),
                    document.getString("Nome"),
                    document.getString("Autor"),
                    document.getString("Email"),
                    document.getString("Lançamento"),
                });
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error: "+e.getMessage());
        }
        
        table.setModel(model);
    }
}
    
