# include <stdlib.h>
# include <stdio.h>
# include <math.h>
# include <time.h>
# include <omp.h>

int main ( );

void ccopy ( int n, double x[], double y[] );
void cfft2 ( int n, double x[], double y[], double w[], double sgn );
void cffti ( int n, double w[] );
double ggl ( double *ds );
void step ( int n, int mj, double a[], double b[], double c[], double d[], 
  double w[], double sgn );
void timestamp ( );

/******************************************************************************/

int main ( )

/******************************************************************************/
/* 
  Purpose:

    MAIN is the main program for FFT_OPENMP.

  Discussion:

    The "complex" vector A is actually stored as a double vector B.

    The "complex" vector entry A[I] is stored as:

      B[I*2+0], the real part,
      B[I*2+1], the imaginary part.

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.
*/
{
  double error;
  int first;
  double flops;
  double fnm1;
  int i;
  int icase;
  int it;
  int ln2;
  int ln2_max = 20;
  double mflops;
  int n;
  int nits = 1000;
  static double seed;
  double sgn;
  double *w;
  double wtime;
  double *x;
  double *y;
  double *z;
  double z0;
  double z1;

  timestamp ( );
  printf ( "\n" );
  printf ( "FFT_OPENMP\n" );
  printf ( "  C/OpenMP version\n" );
  printf ( "\n" );
  printf ( "  Demostracion de la Transformada Rapida de Fourier\n" );
  printf ( " para un vector de datos complejos, por medio de OpenMP.\n" );

  printf ( "\n" );
  printf ( "  Number of processors available = %d\n", omp_get_num_procs ( ) );
  printf ( "  Number of threads =              %d\n", omp_get_max_threads ( ) );
/*
  Prepare for tests.
*/
  printf ( "\n" );
  printf ( "  Accuracy check:\n" );
  printf ( "\n" );
  printf ( "\n" );
  printf ( "             N         Time         MFLOPS\n" );
  printf ( "\n" );

  seed  = 331.0;
  n = 1;
/*
  LN2 is the log base 2 of N.  Each increase of LN2 doubles N.
*/
  for ( ln2 = 1; ln2 <= ln2_max; ln2++ )
  {
    n = 2 * n;
/*
  Allocate storage for the complex arrays W, X, Y, Z.  

  We handle the complex arithmetic,
  and store a complex number as a pair of doubles, a complex vector as a doubly
  dimensioned array whose second dimension is 2. 
*/
    w = ( double * ) malloc (     n * sizeof ( double ) );
    x = ( double * ) malloc ( 2 * n * sizeof ( double ) );
    y = ( double * ) malloc ( 2 * n * sizeof ( double ) );
    z = ( double * ) malloc ( 2 * n * sizeof ( double ) );

    first = 1;

    for ( icase = 0; icase < 2; icase++ )
    {
      if ( first )
      {
        for ( i = 0; i < 2 * n; i = i + 2 )
        {
          z0 = ggl ( &seed );
          z1 = ggl ( &seed );
          x[i] = z0;
          z[i] = z0;
          x[i+1] = z1;
          z[i+1] = z1;
        }
      } 
      else
      {
# pragma omp parallel \
    shared ( n, x, z ) \
    private ( i, z0, z1 )

# pragma omp for nowait

        for ( i = 0; i < 2 * n; i = i + 2 )
        {
          z0 = 0.0;             
          z1 = 0.0;              
          x[i] = z0;
          z[i] = z0;           
          x[i+1] = z1;
          z[i+1] = z1;         
        }
      }
/* 
  Initialize the sine and cosine tables.
*/
      cffti ( n, w );
/* 
  Transform forward, back 
*/
      if ( first )
      {
        sgn = + 1.0;
        cfft2 ( n, x, y, w, sgn );
        sgn = - 1.0;
        cfft2 ( n, y, x, w, sgn );
/* 
  Results should be same as the initial data multiplied by N.
*/
        fnm1 = 1.0 / ( double ) n;
        error = 0.0;
        for ( i = 0; i < 2 * n; i = i + 2 )
        {
          error = error 
          + pow( z[i]   - fnm1 * x[i], 2 )
          + pow( z[i+1] - fnm1 * x[i+1], 2 );
        }
        error = sqrt ( fnm1 * error );
        printf ( "  %12d ", n);
        first = 0;
      }
      else
      {
        wtime = omp_get_wtime ( );
        for ( it = 0; it < nits; it++ )
        {
          sgn = + 1.0;
          cfft2 ( n, x, y, w, sgn );
          sgn = - 1.0;
          cfft2 ( n, y, x, w, sgn );
        }
        wtime = omp_get_wtime ( ) - wtime;

        flops = 2.0 * ( double ) nits 
          * ( 5.0 * ( double ) n * ( double ) ln2 );

        mflops = flops / 1.0E+06 / wtime;

        printf ( "  %12e   %12f\n", wtime, mflops );
      }
    }
    if ( ( ln2 % 4 ) == 0 ) 
    {
      nits = nits / 10;
    }
    if ( nits < 1 ) 
    {
      nits = 1;
    }
    free ( w );
    free ( x );
    free ( y );
    free ( z );
  }
/*
  Terminate.
*/
  printf ( "\n" );
  printf ( "FFT_OPENMP:\n" );
  printf ( "  Normal end of execution.\n" );
  printf ( "\n" );
  timestamp ( );

  return 0;
}
/******************************************************************************/

void ccopy ( int n, double x[], double y[] )

/******************************************************************************/
/*
  Purpose:

    CCOPY copies a complex vector.

  Discussion:

    The "complex" vector A[N] is actually stored as a double vector B[2*N].

    The "complex" vector entry A[I] is stored as:

      B[I*2+0], the real part,
      B[I*2+1], the imaginary part.

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.

  Parameters:

    Input, int N, the length of the vector.

    Input, double X[2*N], the vector to be copied.

    Output, double Y[2*N], a copy of X.
*/
{
  int i;

  for ( i = 0; i < n; i++ )
  {
    y[i*2+0] = x[i*2+0];
    y[i*2+1] = x[i*2+1];
   }
  return;
}
/******************************************************************************/

void cfft2 ( int n, double x[], double y[], double w[], double sgn )

/******************************************************************************/
/*
  Purpose:

    CFFT2 performs a complex Fast Fourier Transform.

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.

  Parameters:

    Input, int N, the size of the array to be transformed.

    Input/output, double X[2*N], the data to be transformed.  
    On output, the contents of X have been overwritten by work information.

    Output, double Y[2*N], the forward or backward FFT of X.

    Input, double W[N], a table of sines and cosines.

    Input, double SGN, is +1 for a "forward" FFT and -1 for a "backward" FFT.
*/
{
  int j;
  int m;
  int mj;
  int tgle;

   m = ( int ) ( log ( ( double ) n ) / log ( 1.99 ) );
   mj   = 1;
/*
  Toggling switch for work array.
*/
  tgle = 1;
  step ( n, mj, &x[0*2+0], &x[(n/2)*2+0], &y[0*2+0], &y[mj*2+0], w, sgn );

  if ( n == 2 )
  {
    return;
  }

  for ( j = 0; j < m - 2; j++ )
  {
    mj = mj * 2;
    if ( tgle )
    {
      step ( n, mj, &y[0*2+0], &y[(n/2)*2+0], &x[0*2+0], &x[mj*2+0], w, sgn );
      tgle = 0;
    }
    else
    {
      step ( n, mj, &x[0*2+0], &x[(n/2)*2+0], &y[0*2+0], &y[mj*2+0], w, sgn );
      tgle = 1;
    }
  }
/* 
  Last pass through data: move Y to X if needed.
*/
  if ( tgle ) 
  {
    ccopy ( n, y, x );
  }

  mj = n / 2;
  step ( n, mj, &x[0*2+0], &x[(n/2)*2+0], &y[0*2+0], &y[mj*2+0], w, sgn );

  return;
}
/******************************************************************************/

void cffti ( int n, double w[] )

/******************************************************************************/
/*
  Purpose:

    CFFTI sets up sine and cosine tables needed for the FFT calculation.

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.

  Parameters:

    Input, int N, the size of the array to be transformed.

    Output, double W[N], a table of sines and cosines.
*/
{
  double arg;
  double aw;
  int i;
  int n2;
  const double pi = 3.141592653589793;

  n2 = n / 2;
  aw = 2.0 * pi / ( ( double ) n );

# pragma omp parallel \
    shared ( aw, n, w ) \
    private ( arg, i )

# pragma omp for nowait

  for ( i = 0; i < n2; i++ )
  {
    arg = aw * ( ( double ) i );
    w[i*2+0] = cos ( arg );
    w[i*2+1] = sin ( arg );
  }
  return;
}
/******************************************************************************/

double ggl ( double *seed )

/******************************************************************************/
/* 
  Purpose:

    GGL generates uniformly distributed pseudorandom real numbers in [0,1]. 

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen, M Troyer, I Vattulainen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.

  Parameters:

    Input/output, double *SEED, used as a seed for the sequence.

    Output, double GGL, the next pseudorandom value.
*/
{
  double d2 = 0.2147483647e10;
  double t;
  double value;

  t = ( double ) *seed;
  t = fmod ( 16807.0 * t, d2 );
  *seed = ( double ) t;
  value = ( double ) ( ( t - 1.0 ) / ( d2 - 1.0 ) );

  return value;
}
/******************************************************************************/

void step ( int n, int mj, double a[], double b[], double c[],
  double d[], double w[], double sgn )

/******************************************************************************/
/*
  Purpose:

    STEP carries out one step of the workspace version of CFFT2.

  Modified:

    20 March 2009

  Author:

    Original C version by Wesley Petersen.
    This C version by John Burkardt.

  Reference:

    Wesley Petersen, Peter Arbenz, 
    Introduction to Parallel Computing - A practical guide with examples in C,
    Oxford University Press,
    ISBN: 0-19-851576-6,
    LC: QA76.58.P47.

  Parameters:

*/
{
  double ambr;
  double ambu;
  int j;
  int ja;
  int jb;
  int jc;
  int jd;
  int jw;
  int k;
  int lj;
  int mj2;
  double wjw[2];

  mj2 = 2 * mj;
  lj  = n / mj2;

# pragma omp parallel \
    shared ( a, b, c, d, lj, mj, mj2, sgn, w ) \
    private ( ambr, ambu, j, ja, jb, jc, jd, jw, k, wjw )

# pragma omp for nowait

  for ( j = 0; j < lj; j++ )
  {
    jw = j * mj;
    ja  = jw;
    jb  = ja;
    jc  = j * mj2;
    jd  = jc;

    wjw[0] = w[jw*2+0]; 
    wjw[1] = w[jw*2+1];

    if ( sgn < 0.0 ) 
    {
      wjw[1] = - wjw[1];
    }

    for ( k = 0; k < mj; k++ )
    {
      c[(jc+k)*2+0] = a[(ja+k)*2+0] + b[(jb+k)*2+0];
      c[(jc+k)*2+1] = a[(ja+k)*2+1] + b[(jb+k)*2+1];

      ambr = a[(ja+k)*2+0] - b[(jb+k)*2+0];
      ambu = a[(ja+k)*2+1] - b[(jb+k)*2+1];

      d[(jd+k)*2+0] = wjw[0] * ambr - wjw[1] * ambu;
      d[(jd+k)*2+1] = wjw[1] * ambr + wjw[0] * ambu;
    }
  }
  return;
}
/******************************************************************************/

void timestamp ( void )

/******************************************************************************/
/*
  Purpose:

    TIMESTAMP prints the current YMDHMS date as a time stamp.

  Example:

    31 May 2001 09:45:54 AM

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    24 September 2003

  Author:

    John Burkardt

  Parameters:

    None
*/
{
# define TIME_SIZE 40

  static char time_buffer[TIME_SIZE];
  const struct tm *tm;
  time_t now;

  now = time ( NULL );
  tm = localtime ( &now );

  strftime ( time_buffer, TIME_SIZE, "%d %B %Y %I:%M:%S %p", tm );

  printf ( "%s\n", time_buffer );

  return;
# undef TIME_SIZE
}