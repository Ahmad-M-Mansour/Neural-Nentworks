package networkneural;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class NetworkNeural 
{ 
    public static Scanner in=new Scanner(System.in);
    public static int z;
    public static int x;
    public static int y;
    public static int n;
    public static int numHidden;
    public static double c;
    public static double q;
    public static NETWORK nn;
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException 
    {  
        boolean f=true;
        boolean test;
        while(f){
        System.out.print("1_Singel Perceptron.\n2_Singel Delta.\n3_R-CategoryPerceptron.\n4_R-CategoryDelta.\n5_BackPropagation_SIN.\n6_BackPropagation_LETTERs.\n7_BackPropagation_IRIS.\n8_ELMAN.\n9_Hopfield.\n10_SOM.\n11_Exit.\nchoose=");
        z=in.nextInt();
        if(z==1||z==2||z==3||z==4){
        System.out.print("\nEnter input Neural=");
        x=in.nextInt();
        System.out.print("\nEnter number training=");
        n=in.nextInt();
        System.out.print("\nEnter  constant learning=");
        c=in.nextDouble();
        if(z==1){//One Neuron Output with Perceptron Neural Network
                System.out.print("\nEnter theta=");
                q=in.nextDouble();
                nn=new NETWORK(x, 1, n, c, q);
                //System.out.print("\nEnter Bias weight=");
                //nn.Bias=in.nextDouble();
            if(nn.SingelPerceptron(x,n))
            {
                System.out.println("process training is finish");
                System.out.println();            
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < 1; j++) {
                        System.out.print("weight["+i+"]["+j+"]="+nn.weight[i][j]+"\t");
                    }
                System.out.println(); 
                }
                //System.out.println("Bias="+nn.Bias);
                System.out.print("OUTPUT is:");
                System.out.println();                
                for (int j = 0; j < n; j++) {
                        System.out.print("output["+0+"]["+j+"]="+nn.output[0][j]+"\t");
                        System.out.println(); 
                }
                System.out.println("--------------------"); 
                test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_P(x, 1);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
            else System.out.println("process training is no finish");
        }
        else if(z==2){//One Neuron Output with Delta Neural Network
            nn=new NETWORK(x, 1, n, c);
            System.out.print("\nEnter Bias weight=");
            nn.Bias=in.nextDouble();
            if(nn.SingelDelta(x,n))
            {
                System.out.print("process training is finish");
                System.out.println();            
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < 1; j++) {
                        System.out.print("weight["+i+"]["+j+"]="+nn.weight[i][j]+"\t");
                    }
                System.out.println(); 
                }
                System.out.println("Bias weight="+nn.Bias);
                System.out.print("OUTPUT is:");
                System.out.println();         
                    for (int j = 0; j < n; j++) {
                        System.out.print("output["+0+"]["+j+"]="+nn.output[0][j]+"\t");       
                        System.out.println(); 
                }
                    System.out.println("--------------------"); 
                    test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_D(x, 1);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
            else System.out.println("process training is no finish");
        }
        if(z==3){//MultiNeuron Output with Perceptron Neural Network
                System.out.print("\nEnter output Neural=");
                y=in.nextInt();
                System.out.print("\nEnter theta=");
                q=in.nextDouble();
                nn=new NETWORK(x, y, n, c, q);
//                System.out.print("\nEnter Bias weight=");
//                nn.Bias=in.nextDouble();
            if(nn.R_CategoryPerceptron(x,y,n))
            {
                System.out.print("process training is finish");
                System.out.println();            
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        System.out.print("weight["+i+"]["+j+"]="+nn.weight[i][j]+"\t");
                    }
                System.out.println(); 
                }
//                System.out.println("Bias weight="+nn.Bias);
                System.out.print("OUTPUT is:");
                System.out.println();
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print("output["+i+"]["+j+"]="+nn.output[i][j]+"\t");
                    }
                System.out.println(); 
                }
                System.out.println("--------------------");
                test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_P(x, y);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
            else System.out.println("process training is no finish");
        }
        if(z==4){//MultiNeuron Output with Delta Neural Network
                System.out.print("\nEnter output Neural=");
                y=in.nextInt();
                nn=new NETWORK(x, y, n, c);
//                System.out.print("\nEnter Bias weight=");
//                nn.Bias=in.nextDouble();
            if(nn.R_CategoryDelta(x,y,n))
            {
                System.out.print("process training is finish");
                System.out.println();            
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        System.out.print("weight["+i+"]["+j+"]="+nn.weight[i][j]+"\t");
                    }
                System.out.println(); 
                }
//                System.out.println("Bias weight="+nn.Bias);
                System.out.print("OUTPUT is:");
                System.out.println();
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < n; j++) {
                        System.out.print("output["+i+"]["+j+"]="+nn.output[i][j]+"\t");
                    }
                System.out.println(); 
                }
                System.out.println("--------------------"); 
                test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_D(x, y);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
            else System.out.println("process training is no finish");
        }
    }
        else if(z==5){//BackPropegation for SIN(4*X1*X2)
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder aw=dbFactory.newDocumentBuilder();
            Document doc = aw.parse("ex2.xml");
            NodeList nList = doc.getElementsByTagName("training");
            System.out.print("Enter  constant learning=");
            c=in.nextDouble();
            System.out.print("\nEnter heddin neural=");
            numHidden=in.nextInt();
            nn=new NETWORK(2, 1, numHidden,nList.getLength(), c);
            System.out.println( doc.getDocumentElement().getNodeName()+"(4*x1*x2):");
            System.out.print("----------------------------");
           if(nn.BackPropegation_SIN(numHidden))
           {
               System.out.println("finish");
               for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < numHidden; j++) {
                        System.out.print("Vweight["+i+"]["+j+"]="+nn.Vweight[i][j]+"\t");
                    }
                     System.out.println(); 
               }
                for (int i = 0; i < numHidden; i++) {
                     System.out.print("weight["+i+"]="+nn.weight[i][0]+"\t");
                }
                System.out.println();
                test=true;
                while(test){
                    int t;
                    System.out.println("--------------------");
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_BP_SIN(2, numHidden);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
           }
           else System.out.println("process training is no finish"); 	
            nn=null;
       }
        else if(z==6){//BackPropegation for LETTERs
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder aw=dbFactory.newDocumentBuilder();
            Document doc = aw.parse("ex1.xml");
            NodeList nList = doc.getElementsByTagName("training");
            System.out.print("Enter  constant learning=");
            c=in.nextDouble();
            System.out.print("\nEnter heddin neural=");
            numHidden=in.nextInt();
            nn=new NETWORK(25, 5,numHidden ,nList.getLength(), c);
            System.out.println( doc.getDocumentElement().getNodeName()+":");
            System.out.print("----------------------------");
           if(nn.BackPropegation_LETTERs(numHidden))
           {
               System.out.println("finish");
               for (int i = 0; i < 25; i++) {
                    for (int j = 0; j < numHidden; j++) {
                        System.out.print("Vweight["+i+"]["+j+"]="+nn.Vweight[i][j]+"\t");
                    }
                     System.out.println(); 
               }
                for (int i = 0; i < numHidden; i++) {
                    for (int j = 0; j < 5; j++) {
                     System.out.print("weight["+i+"]["+j+"]="+nn.weight[i][j]+"\t");
                    }
                    System.out.println(); 
                }
                System.out.println();
                test=true;
                while(test){
                    int t;
                    System.out.println("--------------------");
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING_BP_LETTER(25, numHidden,5);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
           }
           else System.out.println("process training is no finish"); 	
            nn=null;
        }
//        else if(z==7){
//         File fx=new File("IRIS.txt");
//       
//        }
        else if(z==7){//BackPropagation for IRIS Dataset
          System.out.print("Enter Hidden Neuron number =");
          numHidden=in.nextInt();
        nn=new NETWORK(4, 1, numHidden, 150, 0.5);
        if(nn.BackPropagation_IRIS(numHidden))
        {
            System.out.println("sccessed");
            test=true;
                while(test){
                    int t;
                    System.out.println("--------------------");
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) nn.TESTING(4, numHidden,1);
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
        }
        else System.out.println("process training is no finish"); 	
            nn=null;
        }
        else if(z==8){//Elman Neural Network
            double net=0;
            System.out.print("\nEnter input Neural=");
            x=in.nextInt();
            System.out.print("\nEnter output Neural=");
            y=in.nextInt();
            System.out.print("Enter Hidden Neuron number =");
            numHidden=in.nextInt();
            System.out.print("\nEnter  constant learning=");
            c=in.nextDouble();
            n=50;
            nn=new NETWORK(x, y, numHidden, n, c);
            if(nn.Elman(x,y,numHidden,n))
            {
                System.out.println("--------------------"); 
                test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                    if(t==1) {
                        nn.input[0][0]=in.nextDouble();
                        for (int i = 0; i < numHidden; i++) {
                            nn.hidden[i]=nn.input[0][0]*nn.Vweight[0][i];
                        }
                        for (int i = 0; i < numHidden; i++) {
                            for (int j = 0; j < numHidden; j++) {
                                nn.hidden[i]+=nn.Context_Leyar[j]*nn.w_Con_Hid[i][j];
                            }
                        }
                        for (int i = 0; i < numHidden; i++) {
                            net+=nn.hidden[i];
                        }
                        nn.output[0][0]=nn.BIPOLAR(net);
                        System.out.println(nn.output[0][0]);
                    }
                    else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
            else System.out.println("process training is no finish");
        }
        else if(z==9){//Hopfield Neural Network
           nn=new NETWORK();
           nn.Hopfield();
           nn=null;
        }
        else if(z==10){//SOM Neural Network
            int Yi,Yj,R,min_I = 0,min_J = 0;
            double sum=0,MIN_ED=Double.MAX_VALUE;
            System.out.print("\nEnter  Dimension the output layer i=");
            Yi=in.nextInt();
            System.out.print("\nEnter  Dimension the output layer j=");
            Yj=in.nextInt();
            System.out.print("\nEnter  Dimension Edit Neighbourhood=");
            R=in.nextInt();
            System.out.print("\nEnter  constant learning=");
            c=in.nextDouble();
            nn=new NETWORK(4,Yi,Yj,120,R,c);
            if(nn.SOM(4, Yi, Yj,R,c))
            {
                System.out.println("\n--------------------"); 
                test=true;
                while(test){
                    int t;
                    System.out.print("1_TISTING.\n2_BACK.\nchoose=");
                    t=in.nextInt();
                if(t==1){
                nn.input[0][0]=in.nextDouble();
                nn.input[1][0]=in.nextDouble();
                nn.input[2][0]=in.nextDouble();
                nn.input[3][0]=in.nextDouble();
                for(int j1=0; j1<Yi; j1++){
                       for(int j2=0; j2<Yj; j2++){ 
                          for(int i=0; i<4; i++){
                              sum+=Math.pow(nn.input[i][0]-nn.wSOM[i][j1][j2],2);
                          }
                          nn.E_Distance[j1][j2]=Math.sqrt(sum);
                          sum=0;
                        }
                     }
                    //
                    for(int j1=0; j1<Yi; j1++){
                       for(int j2=0; j2<Yj; j2++){
                           if(nn.E_Distance[j1][j2] < MIN_ED){
                               MIN_ED=nn.E_Distance[j1][j2];
                               min_I=j1;
                               min_J=j2;
                           }
                       }
                    }
                    System.out.println("\n"+nn.output[min_I][min_J]);
                    }
                   else if(t==2){
                        test=false;
                    System.out.println("--------------------");
                    }
                    else System.out.println("ERROR.");
                }
                nn=null;
            }
        }
        else if(z==11) {
            f=false;
            System.out.println("----------THANK YOU :):):)----------");
        }
        else System.out.println("ERROR choosen.\n");
        }
    }
}