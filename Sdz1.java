public class Sdz1 {
 
  public static void main(String[] args)
  {
    Ville ville =  new Ville();
    System.out.println(ville.nomVille);
    ville.nomVille = "la tête à toto ! ! ! !";
    System.out.println(ville.nomVille);
                
    Ville ville2 = new Ville("Marseille", 123456789, "France");
    ville2.nomPays = "La tête à tutu ! ! ! ! ";
    System.out.println(ville2.nomPays);
  }
          
}
