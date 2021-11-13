#include  <iostream>
int main(int argc, char** argv) {
int $numero;
int $resultado;
int $i;
printf( "Digite um numero:");
scanf( "%i", &$numero);
printf( "%i",  $numero);
printf("\n");
for( $i=1;$i < 10;$i++){
$resultado = $i*$numero;
printf( "%i",  $i);
printf( " x ");
printf( "%i",  $numero);
printf( " = ");
printf( "%i",  $resultado);
printf("\n");
}
printf( "%i",  $numero);
if ( $numero>10){
printf( "Numero maior que o da tabuada");
}
printf("\n");
if ( $numero<10){
printf( "Numero valido para tabuada");
}
printf("\n");
system("pause");
return 0;
}
