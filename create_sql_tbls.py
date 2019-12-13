import pandas as pd
import os

def main():
    for file in os.listdir("./data"):
        tbl_name = file.split(".")[0]

        df_tbl = pd.read_csv("./data/"+ file)
        index = df_tbl.columns

        for index, row in df_tbl.iterrows():
            statement = "INSERT INTO {table} VALUES ({values})".format(
                table = tbl_name,
                values =  ", ".join([str(x) for x in row])
            )
            print(statement)
            break
        break




if __name__ == '__main__':
    main()
