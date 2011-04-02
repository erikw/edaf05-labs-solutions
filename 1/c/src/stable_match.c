#include "stable_match.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>

FILE *in_stream;
int MAX_LINE = 80;
char mem_error[] = "Memory allocation failed: kill some processes or go buy RAM!\n";

/* An implementation of the Gale-Shapely stable matching algorithm. I have no further comments, no comments att all as you can see  ;-)*/
int main(int argc, char *argv[]) 
{
	bool readsFile = (argc == 2);
	if (readsFile) {
		in_stream = fopen(argv[argc -1], "rt");
	} else {
		in_stream = stdin;
	}
	char init_line[80];
	while ((fetch_line(init_line) != NULL) && (init_line[0] == '#')) { ; }
	int n = calc_n(init_line);
	MAX_LINE = n * nbr_digits(n) + n + 10;
	char line[MAX_LINE];

	char **women = malloc(n * sizeof *women);
	char **men = malloc(n * sizeof *men);
	int **women_prefs = malloc(n * sizeof *women_prefs);
	int **men_prefs = malloc(n * sizeof *men_prefs);
	if ((women == NULL) || (men == NULL) || (women_prefs == NULL) || (men_prefs == NULL)) {
		fprintf(stderr, mem_error);
		exit(EXIT_FAILURE);
	} else {
		for (int w = 0; w < n; ++w) {
			women[w] = malloc(MAX_LINE * sizeof(char) + 1);			
			men[w] = malloc(MAX_LINE * sizeof(char) + 1);
			women_prefs[w] = malloc(n * sizeof(size_t));
			men_prefs[w] = malloc(n * sizeof(size_t));
			if ((women[w] == NULL) || (men[w] == NULL) | (women_prefs[w] == NULL) || (men_prefs[w] == NULL)) {
				fprintf(stderr, mem_error);
				exit(EXIT_FAILURE);
			}
		}
	}
	unsigned int i, z;
	for (i = 0; i < (2 * n); ++i) {
		fetch_line(line);
		z = 0;
		while (line[z++] != ' ') { ; }
		if ((i % 2) == 0) {
			strcpy(men[i / 2], line + z * sizeof(char));
		} else {
			strcpy(women[i / 2], line + z * sizeof(char));
		}
	}
	fetch_line(line);
	int w, p, pref_index, mpref_index;
	char pbuf[nbr_digits(n)];
	for (i = 0; i < (2 * n); ++i) {
		fetch_line(line);
		pref_index = 0;
		mpref_index = 0;
		w = 0;
		while (line[w++] != ':') { ; }
		++w;
		while (line[w] != '\0') {
			p = 0;
			while(line[w] != '\0' && line[w] != ' ') {
				pbuf[p++] = line[w++];
			}
			pbuf[p] = '\0';
			p = atoi(pbuf);
			if ((i % 2) == 0) {
				men_prefs[i / 2][pref_index++] = p / 2;
			} else {
				women_prefs[i / 2][p / 2] = mpref_index++;
			}
			++w;
		}
	}
	int w_partner[n];
	int m_partner[n];
	int m_prop_index[n];
	for (int h = 0; h < n; ++h) {
		w_partner[h] = -1;
		m_prop_index[h] = 0;
	}
	
	typedef struct linked_list list_node;
	list_node *link, *free_man;
	link = NULL;
	for (int f = n - 1; f >= 0; --f) {
		free_man = (list_node *) malloc(sizeof(list_node));
		free_man->id = f;
		free_man->next = link;
		link = free_man;
	}
	free_man = link;
	int next_woman, w_current_partner;
	int dumped_man, index;
	list_node *tmp_link;
	while (free_man) {
		index = m_prop_index[free_man->id]++;
		next_woman = men_prefs[free_man->id][index] - 1;
		w_current_partner = w_partner[next_woman];
		if (w_current_partner == -1) {
			w_partner[next_woman] = free_man->id;
			m_partner[free_man->id] = next_woman;
			tmp_link = free_man->next;
			free(free_man);
			free_man = free_man->next;
		} else if (women_prefs[next_woman][free_man->id] < women_prefs[next_woman][w_current_partner]) {
			dumped_man = w_current_partner;
			w_partner[next_woman] = free_man->id;
			m_partner[free_man->id] = next_woman;
			free_man->id = dumped_man;
		}
	}
	int wife, husband;
	char wife_name[MAX_LINE * sizeof(char) + 1];
	char husband_name[MAX_LINE * sizeof(char) + 1];
	for (husband = 0; husband < n; husband++) {
		wife = m_partner[husband];
		strcpy(husband_name, men[husband]);
		strcpy(wife_name, women[wife]);
		printf("%s -- %s\n", husband_name, wife_name);
	}
	free(women);
	free(men);
	if (readsFile) {
		fclose(in_stream);
	}
	return 0;
}

char* fetch_line(char line[])
{
	char* ret = fgets(line, MAX_LINE, in_stream);
	if (ret != NULL) {
		int len = strlen(line);
		if (line[len - 1] == '\n') {
			line[len - 1] = '\0';
		}
	}
	return ret;
}

int calc_n(char line[])
{
	char nbuf[MAX_LINE];
	int u = 2;
	while (line[u] != '\0') {
		nbuf[u - 2] = line[u];
		++u;
	}
	nbuf[u] = '\0';
	return atoi(nbuf);
}


int nbr_digits(int n)
{
	int nbr =  2 * n;
	int nbr_digits = 0;
	while (nbr > 0) {
		nbr /= 10;
		++nbr_digits;
	}
	return  nbr_digits;
}
