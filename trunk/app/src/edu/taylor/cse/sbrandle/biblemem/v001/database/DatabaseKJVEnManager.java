package edu.taylor.cse.sbrandle.biblemem.v001.database;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import edu.taylor.cse.sbrandle.biblemem.v001.BookObject;
import edu.taylor.cse.sbrandle.biblemem.v001.VerseObject;

/**
 * It deals with English mode phone.
 * It gets English DB for memorization mode.
 * 
 * Date : Jun 12, 2013
 * @author Seungmin Lee, rfrost77@gmail.com
 * @since 1.0v
 * @version "%I%, %G%"
 *
 */
public class DatabaseKJVEnManager extends DatabaseManager {

	public DatabaseKJVEnManager(Context context) throws IOException {
		super(context);
	}



	/*** SQL Methods ***/

	/**
	 * getBookInfo returns two arrays, one containing the book names on the database, and the other one containing the total number
	 * of chapter found in each book.
	 * @return
	 */
	@Override
	public ArrayList<BookObject> getBookList(){

		String sql = "SELECT B.book_id, B.name, chp.total_chapter FROM BookEn B, "
				+ "(SELECT DISTINCT VC.book_id, COUNT(VC.chapter_id) AS total_chapter FROM VerseCount VC GROUP BY VC.book_id) "
				+ "AS chp WHERE chp.book_id=B.book_id ORDER BY B.book_id;";
		Cursor row = db.rawQuery(sql, null);

		ArrayList<BookObject> books = new ArrayList<BookObject>();

		if ((row.getCount() > 0) && (!row.isClosed())) {
			row.moveToFirst();
			do {
				books.add(new BookObject(row.getInt(0), row.getString(1), row.getInt(2)));
			} while(row.moveToNext());
		}

		row.close();
		return books;
	}



	/**
	 * getBookInfo returns two arrays, one containing the book names on the database, and the other one containing the total number
	 * of chapter found in each book.
	 * @return
	 */
	@Override
	public BookObject getBook(int bookId){

		String sql = "SELECT B.book_id, B.name, chp.total_chapter FROM BookEn B,"
				+ " (SELECT DISTINCT VC.book_id, COUNT(VC.chapter_id) AS total_chapter FROM VerseCount VC GROUP BY VC.book_id)"
				+ " AS chp WHERE B.book_id=" + bookId + " AND chp.book_id=B.book_id ORDER BY B.book_id;";
		Cursor row = db.rawQuery(sql, null);

		BookObject book = null;
		if ((row.getCount() > 0) && (!row.isClosed())) {
			row.moveToFirst();
			do {
				book = new BookObject(row.getInt(0), row.getString(1), row.getInt(2));
			} while(row.moveToNext());
		}
		row.close();
		return book;
	}
//
//
//
//	/**
//	 * getVerseWithReference returns two strings, one containing the the reference for a verse, and the other the actual text for the verse
//	 * @param bid Book ID
//	 * @param chpid Chapter Number
//	 * @param vnum Verse Number
//	 * @return
//	 */
//	@Override
//	public String[] getRefVerse(int bid, int chpid, int vnum){
//		String sql = "SELECT DISTINCT B.name, V.chapter, V.verse, V.contents FROM BookEn B, KJV V WHERE B.book_id ="
//				+ bid + " AND V.book=" + bid + " AND V.chapter=" +  chpid + " AND V.verse=" + vnum;
//		Cursor row = db.rawQuery(sql, null);
//
//		String[] refVerse = new String[2];
//		if ((row.getCount() > 0) && (!row.isClosed())) {
//			row.moveToFirst();
//			refVerse[0] = row.getString(0) + " " + (row.getInt(1)+1) +  ":" + row.getInt(2);
//			refVerse[1] = row.getString(3);
//		} 
//		row.close();
//		return refVerse;
//	}



	@Override
	public VerseObject getVerse(int _id){
		String sql = "SELECT DISTINCT _id, vername, book, chapter, verse, contents FROM KJV WHERE _id=" + _id + ";";
		Cursor row = db.rawQuery(sql, null);

		VerseObject verse = null;
		if ((row.getCount() > 0) && (!row.isClosed())) {
			row.moveToFirst();
			verse = new VerseObject(row.getInt(0), row.getString(1), row.getInt(2), row.getInt(3), row.getInt(4), row.getString(5));
		}
		row.close();
		return verse;
	}
}
