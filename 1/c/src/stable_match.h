/* Header file for the stable match program. */
char* fetch_line(char line[]);
int calc_n(char line[]);
int nbr_digits(int n);

struct linked_list
{
	int id;
	struct linked_list *next;
};
