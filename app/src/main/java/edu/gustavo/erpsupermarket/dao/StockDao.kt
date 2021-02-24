package edu.gustavo.erpsupermarket.dao

import androidx.room.Dao
import edu.gustavo.erpsupermarket.entities.Stock

@Dao
interface StockDao : EntityDao<Stock>