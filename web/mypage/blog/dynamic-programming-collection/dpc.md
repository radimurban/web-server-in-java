
    <h2 id="lps">Longest Positive Subsequence</h2>
    <p>

        The task is to program an algorithm that for an array,
        computes the length of its longest positive subarray, where a subarray is called positive if all its elements are positive.
        For example, the longest posistive subarray of the array $A = \{1,-1,3,2,5,-4,5\}$ is $ \{3,2,5\} $, and its length is $ 3 $. 

        $$ 
        
        \begin{array} {|l|c|}
        
        \hline \textbf{A} & 1 & -1 & 3 & 2 & 5 & -4 & 5  \\ 
        \hline \textbf{max} & 1 & 1 & 1 & 2 & 3 & 3 & \color{#bf2a2a}{\textbf{3}}  \\ 
        \hline \textbf{counter} & 1 & 0 & 1 & 2 & 3 & 0 & 1  \\ 
        \hline  \end{array}
        
        

        $$
        $$\mbox{Table of DP entries.} $$

          The running time is  $\mathcal{O}(n)$.
    </p>
    <div style="page-break-after: always;"></div>

    <h2 id="eft">Energy for a Trip</h2>
    <p>
        Your task is to program an algorithm that computes the minimum energy to complete a trip. 
        More formally, you need to compute the minimum energy for moving
         from position  $0$  to position  $n$ . There are walls in some positions, and you can 
        using the following three ways to move.
      </p>
      <ul>
        <li><b>Walk</b>: Move 1 position using 1 unit of energy but the ending 
          position should NOT be a wall. For example, if you are in Position 5 but 
          Position 6 is a wall, then you are not allowed to use Walk.</li>
          <li><b>Climb</b>: Move 1 position using 2 units of energy. </li>
          <li><b>Jump</b>: Move 6 position using 8 units of energy but the ending 
            position should NOT be a wall.</li>
        </ul>
        <p>
        For example, if you are in Position 5, 
        Positions 6-10 are walls, but Position 11 is NOT a wall, the you can use Jump. <br>
        The input is a Boolean array A: for $1\leq i\leq n$, if $A[i]=1$, Position $i$ is a wall, and if $A[i]=0$, Position is Not a Wall.
        <br><br> 
        Formally, we can say:
        $$ dp(i) = \begin{cases} 0 & \mbox{if } i=0, \\ dp(i-1) + 2 & \mbox{road[i] = true,} \\ min \begin{cases} dp(i-1)+1 \\ dp(i-6)+8  \end{cases} & \mbox{road[i] = false and } i>5, \\ dp(i-1)+1 & \mbox{otherwise.} \end{cases} $$
        <br> <br> 
        The DP table could then look like this: <br>
        $$ 
        
        \begin{array} {|l|c|}
        
        \hline \textbf{i} & 0 & 1 & 2 & 3 & 4 & 5 & 6 & 7 & 8 & 9 & 10  \\ 
        \hline \textbf{road} & - & \mbox{true} & \mbox{false} & \mbox{false} & \mbox{true} & \mbox{true} & \mbox{false} & \mbox{true} & \mbox{true} & \mbox{false} & \mbox{true}  \\ 
        \hline \textbf{dp} & 0 & 2 & 3 & 4 & 6 & 8 & 8 & 10 & 12 & 12 & \color{#bf2a2a}{\textbf{14}}  \\ \hline  \end{array}


        $$

        $$\mbox{Table of DP entries.} $$

        The running time is  $\mathcal{O}(n)$.
    </p>

    <div style="page-break-after: always;"></div>
    <h2 id="maximizing-expression">Maximizing an Expression</h2>
    <p>
      The task is to program an algorithm that computes the maximum value
       of a given expression by inserting parentheses. <br>

      More formally, for an arithmetic expression containing n 
      integers and $n-1$ operators each which is either $+$ or $\cdot$, the 
      goal is to maximize the value of the expression by inserting parentheses. <br>
      For instance, if the expression is $7 + 4 + (-3) \cdot 6 + (-5)$, the maximum value is attained by $(((7 + 4) + (- 3)) \cdot 6) + (-5) = 43$.
<br>
      Your solution has to be in $\mathcal{O}(n^3)$ time, where n is the number of integers in the expression.
<br><br>
      <b>Hint</b>: You may require two tables, one for the maximum values and one for the minimum values. 
      In particular, completing either table requires the other table.
<br><br>
      <b>Attention</b>: You need to be very careful about how the signs affect the recursion. 
      For example, if you multiply numbers from intervals $[a,b]$ and $[c,d]$, then the maximum possible 
      value of the product is $b \cdot d$ if all values $a,b,c,d$ are positive. But the maximum is $a \cdot c$ 
      if all values are negative, and it might also be $b \cdot c$ or $a \cdot d$ in other cases.

    </p>
    <div style="page-break-after: always;"></div>
    <h2 id="shortest-common_supersequence">Shortest Common Supersequence</h2>
    <p>
        Program an algorithm that computes the length of the <b>shortest common supersequence</b> between two given seqeuences.  
<br>
        More formally, for an $n$-element sequence $A$ and an $m$-element sequence $B$, the <i>shortest common supersequence</i>  between 
        $A$ and $B$ is the <b>shortest</b> sequence $C$ such that both $A$ and $B$ are a subsequence of $C$.  <br>
        The approach here is as follows:
        <ol>
          <li>Using DP find out, what is</li>
        </ol>
    </p>

    <div style="page-break-after: always;"></div>
    <h2 id="dna_s_a">DNA Sequence Alignment</h2>
    <p>
      A DNA sequence is a string of characters from a 
      four-character alphabet $\{A, T, G,C\}$. For a pair 
      of two strings $x$ and $y$, an alignment is given by 
      inserting gaps into both $x$ and $y$ at arbitrary places 
      until they have the same length. Each insertion of a 
      gap costs $2$. Afterwards, for each position, we have an 
      additional cost of $1$ for each position in which the extended 
      strings do not match. Thus the aligning operations and their costs are:
      </p>
        <ul>
          <li>Inserting a gap costs $2$.</li>
          <li>Aligning two mismatched characters costs $1$.</li>
          <li>Aligning two matched characters costs $0$.</li>
        </ul>
        <p>
      Your task is to compute the minimal cost $c$ of aligning two DNA sequences $x$ and $y$. Note that length of $x$ 
      does not have be the same as length of $y$. <br>
      Let $dp[i][j]$ denote the cost of aligning the strings $x[0..i]$ and $y[0...j]$. The DP will work as follows:
      $$ 
      dp[i][j] = \begin{cases} 2 \cdot i & \mbox{if } j = 0 \\
                              2 \cdot j & \mbox{if } i = 0 \\
                              min \begin{cases} dp[i-1][j-1] + \begin{cases} 0 & \mbox{if } x[i] == y[j] \\
                                                                              1 & \mbox{otherwise} 
                                                               \end{cases} \\
                                                dp[i-1][j]+2 \\
                                                dp[i][j-1]+2 
                                  \end{cases}
                \end{cases}

      
      $$
      We then extract the solution from $dp[\mbox{x.length()}][\mbox{y.length()}]$.
        <br>
      The DP table for $x =$ "TGACA" and $y =$ "GACCA" yields the result $3$ and looks like this:

      $$

      \begin{array} {|c|c|}
      \hline \textbf{DP} & \textbf{-} & \textbf{T} & \textbf{G} & \textbf{A} & \textbf{C} & \textbf{A} \\ 
      \hline \textbf{-} & 0 & 2 & 4 & 6 & 8 & 10 \\ 
      \hline \textbf{G} & 2 & 1 & 3 & 5 & 7 & 9 \\ 
      \hline \textbf{A} & 4 & 2 & 2 & 4 & 6 & 8 \\ 
      \hline \textbf{C} & 6 & 4 & 2 & 3 & 5 & 6 \\ 
      \hline \textbf{C} & 8 & 6 & 4 & 2 & 3 & 5 \\ 
      \hline \textbf{A} & 10 & 8 & 6 & 4 & 3 & \color{#bf2a2a}{\textbf{3}} \\ 
      \hline  
      \end{array}

      $$

      $$
      \mbox{Table of DP entries.}
      
      $$
        This solution works in $\mathcal{O}(x\mbox{.length()} \cdot y\mbox{.length()})$.
    </p>
    
    <script src="https://gist.github.com/radimurban/5fabf48e9192fb5e00aedb373de1440f.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id = "square">Square (Find biggest square 1 matrix)</h2>
    <p>
        You are given a 2-dimensional binary 
        matrix $B$ having $M$  rows and $N$  columns filled with only 0's and 1's. 
        Your task is to find the area of a largest square submatrix that contains only 1's.
    </p>
    <script src="https://gist.github.com/radimurban/80eb61161b2ff5b01f019cd22e7b86e0.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="longest_alternating_subarray">Longest Alternating Subarray</h2>

    <p>
        The task is to program an algorithm that, for an array of distinct integers, computes the length of its longest alternating subarray. A subarray is called alternating if its
         values form a zig-zag pattern. More precisely, for a subarray, write down comparison 
         signs (< and >) between its elements. Then, the subarray is called alternating if the comparison signs form a sequence  $(<, >, <, >, ...)$  or  $(>, <, >, <, ...)$ . <br><br>

To illustrate, consider the subarray  $\{1, 5, 3, 4, 2, 6\} $ . Then the comparison signs are  $(<, >, <, >, <)$ , so the subarray is alternating. On the other hand, consider the subarray  $\{1, 5, 3, 4, 6, 2\}$ . Then the comparison signs are  $(<, >, <, <, >)$ , so the subarray is not alternating.<br><br>

As a final example, for the array  $\{1, 9, 2, 3, 5, 4, 6, 7, 8\}$ , the longest alternating subarray has length  $4$  and corresponds to either  $\{1, 9, 2, 3\}$  or  $\{3, 5, 4, 6\}$ .<br>
    </p>
    <script src="https://gist.github.com/radimurban/0dd0769aa98e2d07a878d51de3ddd34f.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="palindromic-distance">Palindromic Distance</h2>
    <p>
        Given a sequence   $A$   of   $n$   characters, your task is 
        to compute the minimum number of operations that is required to turn   
        $A$   into a <b>palindrome</b>.
        We consider the following operations:
        <ul>
          <li>Change the character at any position</li>
          <li>Remove the character at any position</li>
          <li>Insert a character at any position</li>
        </ul>
        Let $dp(i,j)$ denote the minimum number of operations to convert substring $s[i...j]$ into palindrome. 
        More formally, for string $s$, the palindromic distance is:

        $$ dp(i,j) = \begin{cases} \mbox{0} & \mbox{if } i \geq j \\ \mbox{dp(i+1, j-1)} & \mbox{if } s[i] == s[j] \\ \mbox{1 + min} \begin{cases} \mbox{dp(i+1, j-1)} & \mbox{(1)} \\ \mbox{dp(i+1, j)} & \mbox{(2)} \\ \mbox{dp(i, j-1)} & \mbox{(3)} \end{cases} \end{cases} $$
        The operations $ (1), (2), (3)$ correspond to following operations: 
        <br><br>
        $(1)$ - <i> <b>replace</b> characters of $s[i]$ to $s[j]$ or $s[j]$ to $s[i]$</i> <br>
         $(2)$ - <i> <b>remove</b> $i$-th character or <b>insert</b> a new character right after $j$-th position</i> <br>
          $(3)$ - <i> <b>remove</b> $j$-th character or <b>insert</b> a new character right before $i$-th position</i> <br> <br>
          
        For example, if   $s$   is "ETHZETHZ", the answer is $3$.

        $$

        \begin{array} {|c|c|}
        
        \hline \textbf{DP} & \textbf{E} & \textbf{T} & \textbf{H} & \textbf{Z} & \textbf{E} & \textbf{T} & \textbf{H} & \textbf{Z} \\ 
        \hline \textbf{E} & 0 & 1 & 1 & 2 & 1 & 2 & 2 & \color{#bf2a2a}{\textbf{3}} \\ 
        \hline \textbf{T} & 0 & 0 & 1 & 1 & 2 & 1 & 2 & 2 \\ 
        \hline \textbf{H} & 0 & 0 & 0 & 1 & 1 & 2 & 1 & 2 \\ 
        \hline \textbf{Z} & 0 & 0 & 0 & 0 & 1 & 1 & 2 & 1 \\ 
        \hline \textbf{E} & 0 & 0 & 0 & 0 & 0 & 1 & 1 & 1 \\ 
        \hline \textbf{T} & 0 & 0 & 0 & 0 & 0 & 0 & 1 & 1 \\ 
        \hline \textbf{H} & 0 & 0 & 0 & 0 & 0 & 0 & 0 & 1 \\ 
        \hline \textbf{Z} & 0 & 0 & 0 & 0 & 0 & 0 & 0 & 0 \\ 
        \hline  
        
        \end{array}
        
        $$

        $$ \mbox{DP table entries. The result is at dp[0][n-1].} $$

    </p>
    <script src="https://gist.github.com/radimurban/5be5b81cfa54b8dcaf7df635aa7da47f.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="shuffle">Shuffle</h2>
    <p>
        Given three strings  $A, B, C$  with  $|A|=n,  |B|=m , |C|=n+m$ , your 
        task is to decide if  $C$  is a <b>shuffle</b> of  $A$  and  $B$.
        A <b>shuffle</b> of  $A$  and  $B$  is formed by merging  $A$  and  $B$
          into a new string 
        while maintaining both the internal order of the characters of $A$  and the 
        internal order of the characters of  $B$ . 
        For example,  $C  =$ <b>P</b>INE<b>AP</b>P<b>LE</b>  is a shuffle of  $A  =$ <b>PAPLE</b> 
        and  $B  = $ INEP.
    </p>
    <script src="https://gist.github.com/radimurban/c8eae0c241af2beacd6777cec5583a9e.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="longest_pali_subs">Longest Palindromic Subsequence
    </h2>
    <p>
        Given a sequence  $A$  of  $n$  characters, your task is to compute the 
         length of the longest palindromic subsequence of  $A$ , i.e., 
        the length of the longest subsequence of  $A$  that is a 
        palindrome. <br><br>
        For instance, if  $A$  is <b>"ETHZEBEHU"</b>, <b>"HEBEH"</b> is the longest palindromic 
        subsequence of  $A$ , and its length is $5$.

        $$ 
        \begin{array} {|c|c|} \hline \textbf{DP} & \textbf{E} & \textbf{T} & \textbf{H} & \textbf{Z} & \textbf{E} & \textbf{B} & \textbf{E} & \textbf{H} & \textbf{U} \\ 
                            \hline \textbf{E} & \textbf{1} & 1 & 1 & 1 & 3 & 3 & 3 & 5 & \color{#BF2A2A}{\textbf{5}} \\ 
                            \hline \textbf{T} & 0 & \textbf{1} & 1 & 1 & 1 & 1 & 3 & 5 & 5 \\ 
                            \hline \textbf{H} & 0 & 0 & \textbf{1} & 1 & 1 & 1 & 3 & 5 & 5 \\ 
                            \hline \textbf{Z} & 0 & 0 & 0 & \textbf{1} & 1 & 1 & 3 & 3 & 3 \\ 
                            \hline \textbf{E} & 0 & 0 & 0 & 0 & \textbf{1} & 1 & 3 & 3 & 3 \\ 
                            \hline \textbf{B} & 0 & 0 & 0 & 0 & 0 & \textbf{1} & 1 & 1 & 1 \\ 
                            \hline \textbf{E} & 0 & 0 & 0 & 0 & 0 & 0 & \textbf{1} & 1 & 1 \\ 
                            \hline \textbf{H} & 0 & 0 & 0 & 0 & 0 & 0 & 0 & \textbf{1} & 1 \\ 
                            \hline \textbf{U} & 0 & 0 & 0 & 0 & 0 & 0 & 0 & 0 & \textbf{1} \\ 
                            \hline  
                            
                            
        \end{array} 
        
        \\
        
        $$
        $$ \mbox{Table showing the entries of the DP table. The result is at dp[0][n-1] and the base cases are in } \textbf{bold}.$$

        The running time should be $ \mathcal{O}(n^2)$.

    </p>
    <script src="https://gist.github.com/radimurban/96aa3a6c3d8ee6b69ae68826ade1f813.js"></script>
    <div style="page-break-after: always;"></div>

    <h2 id="art_gal">Art Gallery
    </h2>
    <p>
      A gang of thieves is robbing an art gallery. They own a truck with a volume of $V$  
      which they 
      plan to fill with valuable sculptures. There are $n$  sculptures in the 
      gallery, the $n$-th of which is guarded by a sophisticated alarm system 
      that requires $t \geq 0$  minutes to disable. The $i$-th  sculpture 
      has a value  $p_i > 0$  on the black market and occupies 
      $v_i > 0$ in the truck.
      <br><br>
      The thieves only have $T$ minutes before the police arrives. 
      The task is to design an algorithm that computes the maximum amount of money  
      they can make form the heist.
      <br><br>
      The program should run in time $ \mathcal{O}(nVT) $ with reasonable hidden constants).
    </p>
    <script src="https://gist.github.com/radimurban/7fa05a67c25acd2192aa2b0ac7c1d368.js"></script>

    <div style="page-break-after: always;"></div>
<h2 id="ar_partition">Partition of an Array
    </h2>
    <p>
        You are given an array of $n$ natural numbers $ a_1,...,a_n  \in \mathbb{N}$ summing to $A$, which is a multiple of $3$. <br>
        You want to determine whether it is possible to partition $\{1, . . . , n\}$ into three disjoint subsets $I, J, K$ such that the corresponding elements of the array yield the same sum, that is $|I| = |J| = |K| = \frac{A}{3}$.
        <br><br>
For example, the answer for the input $\{2,4,8,1,4,5,3\}$ is <b>yes</b>, because there is the partition $(3,4), (2,6), (1,5,7)$ (corresponding to the subarrays $\{8,1\}, \{4,5\}, \{2,4,3\}$, which are all summing to $9$). 
On the other hand, the answer for the input $\{3,2,5,2\}$ is <b>no</b>. 
Provide a dynamic programming algorithm that determines whether such a partition exists. <br> 
Your algorithm should have an $ \mathcal{O}(nA^2)$ runtime.
    </p>
    <script src="https://gist.github.com/radimurban/1b9f031dcf8c84c2656c223a2f5598a5.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="grid_traversal">
      Grid Traversal
    </h2>
    <p>


      Given is a square grid of size $N\times N$. Rows and columns are indexed from $0$ to $N-1$ and each cell at index (row, column) contains a positive value (its cost) as shown in the example below. You are asked to traverse the grid from the top row to the bottom row, one row at a time. In each step, you can move from a cell $(i,j)$ in row $i$ to either of the cells $(i + 1, j -1)$, $(i + 1, j)$, or
 $(i + 1; j + 1)$ in row $i + 1$. As start you can pick any cell in the first row. <br> The
overall cost of a traversal is the sum of the costs of all visited cells, including start and end.
<br> <br>
Devise an algorithm to find a path from top to bottom of the grid that minimizes the overall
traversal cost.
<br> The algorithm should work in $ \mathcal{O}(N^2) $.
    </p>
    <script src="https://gist.github.com/radimurban/8a7f31c0fa170f087322a02ff8f6ec45.js"></script>
    <div style="page-break-after: always;"></div>

    <h2 id="edit_distance">
      Levenshtein (Edit) Distance
    </h2>

    <p>
     <b>Levenshtein distance</b> is a string metric 
      for measuring the difference between two sequences. <br>
      Informally, the Levenshtein distance between two words is the minimum number of 
      single-character edits (insertions, deletions or substitutions) required to change one word into the other.
      <br>
      The expected runtime is $ \mathcal{O}(|a|\cdot|b|) $.
      <br><br>
      More formally, for two strings $a$ and $b$, the Leveshtein (Edit) Distance is:
      $$ dp(i,j) = \begin{cases} \mbox{i} & \mbox{if } j==0 \\ \mbox{j} & \mbox{if } i==0 \\ \mbox {dp(i-1, j-1)} & \mbox{if } a[i] == a[j] \\ \mbox{1+min} \begin{cases} \mbox{dp(i-1, j)} \\ \mbox{dp(i, j-1)} \\ \mbox{dp(i-1, j-1)} \end{cases} & \mbox{otherwise} \end{cases} $$
      <br>
      

      <b>Example: </b>For strings $a = $ "OTTAWA" and $b = $ "ORLOVA", the result is $4$ and we would get following DP table:

      $$ 
      
      \begin{array} {|c|c|}
      
      \hline \textbf{DP} & \textbf{-} & \textbf{O} & \textbf{T} & \textbf{T} & \textbf{A} & \textbf{W} & \textbf{A} \\ 
      \hline \textbf{-} & 0 & 1 & 2 & 3 & 4 & 5 & 6 \\ 
      \hline \textbf{O} & 1 & 0 & 1 & 2 & 3 & 4 & 5 \\ 
      \hline \textbf{R} & 2 & 1 & 1 & 2 & 3 & 4 & 5 \\ 
      \hline \textbf{L} & 3 & 2 & 2 & 2 & 3 & 4 & 5 \\ 
      \hline \textbf{O} & 4 & 3 & 3 & 3 & 3 & 4 & 4 \\ 
      \hline \textbf{V} & 5 & 4 & 4 & 4 & 4 & 4 & 5 \\ 
      \hline \textbf{A} & 6 & 5 & 5 & 5 & 5 & 5 & \color{#BF2A2A}{\textbf{4}} \\ 
      \hline  \end{array}
      

      $$

      $$\mbox{Table with the DP entries.}$$

      Code implementation:

    </p>
    <script src="https://gist.github.com/radimurban/9ad066b951d3ad4d0fa456402593fd26.js"></script>
    <div style="page-break-after: always;"></div>
    <h2 id="array_sums">
      Is A and B particular sum of array elements?
    </h2>
    <p>
      Given is an array $X$ with $n$ elements $(x_1 ,... , x_n)$. Additionally, two numbers $A$ and $B$ are given.
      We want to find out if $A$ is a sum of elements in $X$ and $B$ is a sum of elements squared and in $X$. <br> Formally, we want to know, if 
      $
      A = \sum_{i=1}^{n} (x_i)
      $ and
      $
      B = \sum_{i=1}^{n} (x_i^2)
      $ is true. <br>
      Let's define the $dp$ table with dimension $(n+1) \times (A+1) \times (B+1)$ and the recursion as follows:
      $$ 
      dp[i][j][k] = \begin{cases} \mbox{true} & \mbox{if } i=j=k=0 \\
                                  \mbox{false} & \mbox{if } i=0 \mbox{ and } i \neq j \mbox{ and } i \neq k \\ 
                                  dp[i-1][j][k] \mbox{ or } dp[i-1][j-x_i][k-x_i^2] & \mbox{otherwise} \end{cases}
      $$
      We can implement this in $\mathcal{O}(nAB)$. The solution is at $dp[n][A][B]$.
    </p>

    <script src="https://gist.github.com/radimurban/f8aeee6bec6eea85805c2266b2f8cb50.js"></script>
