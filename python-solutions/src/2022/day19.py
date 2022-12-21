import fileinput, re

r = 1
o = [ ( t - 1 ) * t // 2 for t in range( 32 + 1 ) ]
p = [ list( map( int, re.findall( "-?\d+", l ) ) ) for l in fileinput.input() ]
for n, a, b, c, d, e, f in p[ : 3 ]:
    m = 0
    mi, mj, mk = max( a, b, c, e ), d, f
    def dfs( t, g,         # t:time remaining, g:goal robot type
             i, j, k, l,   # i:ore, j:clay, k:obsidian, l:geode robots
             w, x, y, z ): # w:ore, x:clay, y:obsidian, z:geode available
        global m
        if ( g == 0 and i >= mi or
             g == 1 and j >= mj or
             g == 2 and ( k >= mk or j == 0 ) or
             g == 3 and k == 0 or
             z + l * t + o[ t ] <= m ):
            return
        while t:
            if g == 0 and w >= a:
                for g in range( 4 ):
                    dfs( t - 1, g, i + 1, j, k, l, w - a + i, x + j, y + k, z + l )
                return
            elif g == 1 and w >= b:
                for g in range( 4 ):
                    dfs( t - 1, g, i, j + 1, k, l, w - b + i, x + j, y + k, z + l )
                return
            elif g == 2 and w >= c and x >= d:
                for g in range( 4 ):
                    dfs( t - 1, g, i, j, k + 1, l, w - c + i, x - d + j, y + k, z + l )
                return
            elif g == 3 and w >= e and y >= f:
                for g in range( 4 ):
                    dfs( t - 1, g, i, j, k, l + 1, w - e + i, x + j, y - f + k, z + l )
                return
            t, w, x, y, z = t - 1, w + i, x + j, y + k, z + l
        m = max( m, z )
    for g in range( 4 ):
        dfs( 32, g, 1, 0, 0, 0, 0, 0, 0, 0 )
    r *= m
print( r )
