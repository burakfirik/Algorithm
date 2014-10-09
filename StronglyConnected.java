import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
 
public class StronglyConnected
{
	
	 public static void main(String[] args)
	    {
	    	
	    	
	    	Comparator<ArrayList<Integer>>myComp=new Comparator<ArrayList<Integer>>(){
	    			
	    		public int compare(ArrayList<Integer>l1,ArrayList<Integer>l2){
	    			
	    			if(l1.get(0)<l2.get(0)){
	    				return -1;
	    			}else if(l1.get(0)==l2.get(0)){
	    				return 0;
	    			}else{
	    				return 1;
	    			}
	    			
	    		}
	    		
	    	};
	    	
	        StronglyConnected k = new StronglyConnected();

	        
	        BufferedReader read=null;
	        int V=-1;
	        int E=-1;
	        ArrayList<ArrayList<Integer>> g =null;
	        try{
	            read=new BufferedReader(new FileReader(args[0]));
	            
	            String cLine=null;
	            //first line number of vertex and number of edges
	            cLine = read.readLine();
	            V=Integer.parseInt(cLine.split(" ")[0]);
	            E=Integer.parseInt(cLine.split(" ")[1]);
	            
	           g= new ArrayList<ArrayList<Integer>>();
	            for (int i = 0; i < V; i++)
	                g.add(new ArrayList<Integer>());
	            
	                        
	            while ((cLine = read.readLine()) != null) {
	            	 int src=Integer.parseInt(cLine.split(" ")[0])-1;
	                 int target=Integer.parseInt(cLine.split(" ")[1])-1;
	                 g.get(src).add(target);
					
				}
	               
	        }catch(Exception e){
	        	System.out.println("File has not been read");
	        }finally{
	            try{
	                if(read!=null) read.close();
	            }catch(Exception e){
	            	System.out.println("File has not been read");
	            }
	        }
	                
	        ArrayList<ArrayList<Integer>> strongComponents = k.getSCComponents(g);

	        for(int i=0;i<strongComponents.size();i++){
	        	
	        	for(int j=0;j<strongComponents.get(i).size();j++){
	        		strongComponents.get(i).set(j,strongComponents.get(i).get(j)+1);
	        	}
	        	Collections.sort(strongComponents.get(i));
	        }
	        Collections.sort(strongComponents,myComp);
	        
	        
	        for(int i=0;i<strongComponents.size();i++){
	        	strongComponents.get(i).add(0,strongComponents.get(i).size());
	        }

	        int num=strongComponents.size();
	        
	        PrintWriter writer=null;
			try {
				writer = new PrintWriter(args[1], "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	        writer.println(num);
	        for(int i=0;i<strongComponents.size();i++){
	        	
	        	for(int j=0;j<strongComponents.get(i).size();j++){
	        		writer.print(strongComponents.get(i).get(j));
	        		if(j+1!=strongComponents.get(i).size()){
	        			writer.print(" ");
	        		}
	        	}
	        	writer.println();
	        }
	        writer.close();
	        
	        
	        System.out.println(strongComponents);    

	    }  
	
	
    public ArrayList<Integer> orderIn(ArrayList<ArrayList<Integer>> graph, boolean[] visited) 
    {        
        int V = graph.size();
        ArrayList<Integer> order = new ArrayList<Integer>();
 
        for (int i = 0; i < V; i++)
            if (!visited[i])
                depthSearch(graph, i, visited, order);
        return order;
    }
    public ArrayList<ArrayList<Integer>> reverse(ArrayList<ArrayList<Integer>> graph)
    {
        int V = graph.size();
       ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < V; i++)
            g.add(new ArrayList<Integer>());
        for (int v = 0; v < V; v++)
            for (int i = 0; i < graph.get(v).size(); i++)
                g.get(graph.get(v).get(i)).add(v);
        return g;
    }
    

    public void depthSearch(ArrayList<ArrayList<Integer>>g, int v, boolean[] visited, ArrayList<Integer> comp) 
    {
        visited[v] = true;
        for (int i = 0; i < g.get(v).size(); i++)
            if (!visited[g.get(v).get(i)])
                depthSearch(g, g.get(v).get(i), visited, comp);
        comp.add(v);
    }
    
  
    public ArrayList<ArrayList<Integer>> getSCComponents(ArrayList<ArrayList<Integer>> g)
    {
        int V = g.size();
        boolean[] visited = new boolean[V];
        ArrayList<Integer> order = orderIn(g, visited);       
       //reverse the graph
        ArrayList<ArrayList<Integer>> reverseG = reverse(g);        
        visited = new boolean[V];

        Collections.reverse(order);

        ArrayList<ArrayList<Integer>> sCompGraph = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < order.size(); i++)
        {
            
            int v = order.get(i);
            if (!visited[v]) 
            {
                ArrayList<Integer> comp = new ArrayList<Integer>();
                depthSearch(reverseG, v, visited, comp);
                sCompGraph.add(comp);
            }
        }
        return sCompGraph;
    }

     
}