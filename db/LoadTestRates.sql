ALTER TABLE "Exchange_Rates" ALTER COLUMN "Exchange_Rate" TYPE numeric (15,6);

COPY "Exchange_Rates" ("Starting_Currency", "Ending_Currency", "Exchange_Rate", "Insert_Dt_Time")
FROM '/Users/Josh/Documents/CMSC 495/TestRates.txt' --Enter the path where you saved the file
WITH (FORMAT text);