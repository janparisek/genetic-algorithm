# Optimization of fitness proportional selection algorithm
## First round
* Fitness-proportional selection
* 100k generations

### Pre-fix
* 808.795s
* 820.510s
* 797.427s

AVG: 13m 28s 911ms

### Remove calls to IndexOutOfBoundsException
* 448.313s
* 445.813s
* 457.044s

AVG: 7m 30s 390ms

### Optimize fitness function
* 401.805s
* 392.631s
* 379.402s

AVG: 6m 31s 279ms

## Second round
* Tournament selection @10 candidates
* SEQ60
* Size 1k
* 100k generations
* Intra-generational diversity
* 1.25 base rate
* 125 crossovers

### Pre-fix
* 1074.268s
* 1064.571s
* 1074.245s

AVG: 17m 51s 028ms

### Callable thread pool
* 493.838s
* 489.762s
* 480.221s

AVG: 8m 07s 940ms

### Runnable thread pool
* 1
* 2
* 3

AVG: 